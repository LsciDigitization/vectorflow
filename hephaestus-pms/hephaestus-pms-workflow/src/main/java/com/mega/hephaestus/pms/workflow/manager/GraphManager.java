package com.mega.hephaestus.pms.workflow.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Streams;
import com.mega.component.bioflow.flow.*;
import com.mega.component.bioflow.step.*;
import com.mega.component.bioflow.task.ProcessId;
import com.mega.component.bioflow.task.ProjectId;
import com.mega.component.nuc.step.StepType;
import com.mega.hephaestus.pms.data.model.entity.HephaestusStageTask;
import com.mega.hephaestus.pms.data.mysql.entity.ProcessLabwareEntity;
import com.mega.hephaestus.pms.workflow.manager.dynamic.ProcessLabwareManager;
import com.mega.hephaestus.pms.workflow.manager.plan.ExperimentStageTaskManager;
import com.mega.hephaestus.pms.workflow.manager.plan.StepManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/4 14:04
 */
@Component
public class GraphManager {

    @Autowired
    private ExperimentStageTaskManager experimentStageTaskManager;
    @Autowired
    private ProcessLabwareManager processLabwareManager;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StepManager stepManager;

    /**
     * load steps
     *
     * @param projectId project id
     * @param processId process id
     * @return steps
     */
    public GraphNodeGroup loadGraphNodeGroup(ProjectId projectId, ProcessId processId, Experiment experiment) {
        GraphNodeGroup graphNodeGroup = new GraphNodeGroup();
        GraphExperimentStepFilter stepTypeFilter = new GraphExperimentStepFilter(projectId, processId, stepManager);
        List<ExperimentStep> steps = experiment.getSteps();

        int stepCount = steps.size();
        List<Point> stepPoints = GraphNodeGenerator.generateHorizontalLayout(stepCount, 0, 0, 250);

        // Step遍历
        IntStream.range(0, stepCount).forEach(m -> {
            Step step = (Step) steps.get(m);
            Point stepPoint = stepPoints.get(m);

            List<Branch> branches = step.getBranches();
            int branchSize = branches.size();
            List<Point> branchPoints = GraphNodeGenerator.generateVerticalLayout(branchSize, stepPoint.x, stepPoint.y, 200);

            // Branch遍历
            IntStream.range(0, branchSize).forEach(n -> {

                Point branchPoint = branchPoints.get(n);
                Branch branch = branches.get(n);

                String stepId = step.getCode() + "-" + branch.getBranchKey();
                GraphNode stepNode = new GraphNode(stepId, GraphNode.Type.DEFAULT, branchPoint, new GraphNode.Style(170, 140), new GraphNode.Data(branch.getBranchName()), null, null);


                List<PlateLine> plates = branch.getPlates();
                int plateSize = plates.size();

                // Plate遍历
                IntStream.range(0, plateSize).forEach(y -> {
                    PlateLine plateLine = plates.get(y);

                    GraphNode.Type nodeType;
                    if (StepLocation.START.equals(step.getLocation())) {
                        nodeType = GraphNode.Type.INPUT;
                    } else if (StepLocation.END.equals(step.getLocation())) {
                        nodeType = GraphNode.Type.OUTPUT;
                    } else {
                        nodeType = GraphNode.Type.DEFAULT;
                    }

                    stepNode.addChild(new GraphNode(plateLine.getId(), nodeType, null, null, new GraphNode.Data(plateLine.getName()), null, null, new GraphNode.CustomData(plateLine.getPlateKey()), GraphNode.Position.LEFT, GraphNode.Position.RIGHT));
                });

                List<GraphNode> children = stepNode.getChildren();
                int size = children.size();
                List<Point> points = GraphNodeGenerator.generateVerticalLayout(size, 10, 40, 50);
                IntStream.range(0, children.size()).forEach(i -> {
                    GraphNode childNode = children.get(i);
                    Point childPoint = points.get(i);
                    childNode.setPosition(childPoint);
                });

                // resize height
                if (points.size() > 2) {
                    Point lastPoint = points.get(points.size() - 1);
                    int resizeHeight = Math.max(stepNode.getStyle().getHeight(), lastPoint.y + 80);
                    stepNode.getStyle().setHeight(resizeHeight);

                    // auto resize branch height
                    double resizeY = Math.max(stepNode.getPosition().getY(), n * (lastPoint.y + 400));
                    stepNode.getPosition().y = (int) resizeY;
                }

                // add node
                graphNodeGroup.addNode(stepNode);

            });

        });

        return graphNodeGroup;
    }

    /**
     * load steps
     *
     * @param projectId project id
     * @param processId process id
     * @return steps
     */
    public GraphNodeGroup loadGraphNodeGroup2(ProjectId projectId, ProcessId processId, List<StepType> steps) {
        GraphNodeGroup graphNodeGroup = new GraphNodeGroup();
        GraphExperimentStepFilter stepTypeFilter = new GraphExperimentStepFilter(projectId, processId, stepManager);

        List<ProcessLabwareEntity> labwareEntities = processLabwareManager.getProcessLabwaresByProcessId(processId.getLongId());
        int stepCount = steps.size();
        List<Point> stepPoints = GraphNodeGenerator.generateHorizontalLayout(stepCount, 0, 0, 250);
        IntStream.range(0, steps.size()).forEach(m -> {
            StepType step = steps.get(m);
            Point stepPoint = stepPoints.get(m);

            GraphNode graphNode = new GraphNode(step.getCode(), GraphNode.Type.GROUP, stepPoint, new GraphNode.Style(170, 140), new GraphNode.Data(step.name()), null, null);

            labwareEntities.forEach(labwareEntity -> {
                Optional<ProcessLabwareEntity> processLabwareEntityOptional = processLabwareManager.getByExperimentIdAndProcessId(labwareEntity.getExperimentId(), processId.getLongId());
                String labwareType = processLabwareEntityOptional.map(ProcessLabwareEntity::getLabwareType).orElse(null);
                List<HephaestusStageTask> stageTasks = experimentStageTaskManager.getStageTasks(labwareEntity.getExperimentId(), step);
                stageTasks.forEach(stageTask -> {
                    GraphNode.Type nodeType;
                    if (stepTypeFilter.getFirstStep().getCode().equals(stageTask.getStepKey())) {
                        nodeType = GraphNode.Type.INPUT;
                    } else if (stepTypeFilter.getLastStep().getCode().equals(stageTask.getStepKey())) {
                        nodeType = GraphNode.Type.OUTPUT;
                    } else {
                        nodeType = GraphNode.Type.DEFAULT;
                    }
                    graphNode.addChild(new GraphNode(stageTask.getId().toString(), nodeType, null, null, new GraphNode.Data(labwareType), null, null, new GraphNode.CustomData(stageTask.getExperimentId().toString()), GraphNode.Position.LEFT, GraphNode.Position.RIGHT));
                });
            });

            List<GraphNode> children = graphNode.getChildren();
            int size = children.size();
            List<Point> points = GraphNodeGenerator.generateVerticalLayout(size, 10, 30, 50);
            IntStream.range(0, children.size()).forEach(i -> {
                GraphNode childNode = children.get(i);
                Point childPoint = points.get(i);
                childNode.setPosition(childPoint);
            });

            // resize height
            if (points.size() > 2) {
                Point lastPoint = points.get(points.size() - 1);
                int resizeHeight = Math.max(graphNode.getStyle().getHeight(), lastPoint.y + 50);
                graphNode.getStyle().setHeight(resizeHeight);
            }

            // add node
            graphNodeGroup.addNode(graphNode);
        });

        return graphNodeGroup;
    }

    @SuppressWarnings("unused")
    public EdgeGroup getEdgeGroup(ProjectId projectId, ProcessId processId) {
        EdgeGroup edgeGroup = new EdgeGroup();

        stepManager.groupByPlateKey(projectId, processId)
                .forEach((plateKey, plateLines) -> {
                    Streams.zip(plateLines.stream(), plateLines.stream().skip(1), (plateLine1, plateLine2) -> {
                        edgeGroup.addEdge(new Edge(String.format("%s-%s", plateLine1.getId(), plateLine2.getId()), plateLine1.getId(), plateLine2.getId(), null, null, new Edge.MarkerEnd(Edge.MarkerType.ArrowClosed)));
                        return null;
                    }).count();
                });

        return edgeGroup;
    }

    @SuppressWarnings("unused")
    @Deprecated(since = "20230407")
    public EdgeGroup getEdgeGroup(GraphNodeGroup graphNodeGroup) {
        EdgeGroup edgeGroup = new EdgeGroup();
        List<GraphNode> nodes = graphNodeGroup.getNodes();
        Streams.zip(nodes.stream(), nodes.stream().skip(1), (node1, node2) -> {
            List<GraphNode> children = node1.getChildren();
            List<GraphNode> children2 = node2.getChildren();
            children.forEach(child -> children2.stream()
                    .filter(child2 -> child.getCustomData().getPlateKey().equals(child2.getCustomData().getPlateKey()))
                    .findFirst()
                    .ifPresent(child2 -> {
                        edgeGroup.addEdge(new Edge(String.format("%s-%s", child.getId(), child2.getId()), child.getId(), child2.getId(), null, null, new Edge.MarkerEnd(Edge.MarkerType.ArrowClosed)));
                    })
            );
            return null;
        }).count();
        return edgeGroup;
    }


    /**
     * get flat nodes
     *
     * @param projectId project id
     * @param processId process id
     * @return flat nodes
     */
    public GraphNodeGroup getGraphNodeGroup(ProjectId projectId, ProcessId processId) {
        GraphExperimentStepFilter stepTypeFilter = new GraphExperimentStepFilter(projectId, processId, stepManager);
//        List<StepType> steps = stepTypeFilter.getSteps();
        return loadGraphNodeGroup(projectId, processId, stepTypeFilter.getExperiment());
    }

    /**
     * get flat nodes
     *
     * @param projectId project id
     * @param processId process id
     * @return flat nodes
     */
    public List<FlatNode> getFlatNodes(ProjectId projectId, ProcessId processId) {
        GraphExperimentStepFilter stepTypeFilter = new GraphExperimentStepFilter(projectId, processId, stepManager);
//        List<StepType> steps = stepTypeFilter.getSteps();
        GraphNodeGroup graphNodeGroup = loadGraphNodeGroup(projectId, processId, stepTypeFilter.getExperiment());

        return graphNodeGroup.toFlatNodes();
    }

    /**
     * convert flat nodes to json
     *
     * @param projectId project id
     * @param processId process id
     * @return json string
     * @throws JsonProcessingException json processing exception
     */
    public String getFlatNodesAsJson(ProjectId projectId, ProcessId processId) throws JsonProcessingException {
        List<FlatNode> flatNodes = getFlatNodes(projectId, processId);
        return objectMapper.writeValueAsString(flatNodes);
    }

    /**
     * convert edges to json
     *
     * @return json string
     * @throws JsonProcessingException json processing exception
     */
    public String getFlatNodesAsJson(GraphNodeGroup graphNodeGroup) throws JsonProcessingException {
        List<FlatNode> flatNodes = graphNodeGroup.toFlatNodes();
        return objectMapper.writeValueAsString(flatNodes);
    }

    /**
     * convert edges to json
     *
     * @return json string
     * @throws JsonProcessingException json processing exception
     */
    public String getEdgesAsJson(GraphNodeGroup graphNodeGroup) throws JsonProcessingException {
        EdgeGroup edgeGroup = getEdgeGroup(graphNodeGroup);
        return objectMapper.writeValueAsString(edgeGroup.getEdges());
    }

    /**
     * convert edges to json
     * @param edgeGroup edge group
     * @return json string
     * @throws JsonProcessingException json processing exception
     */
    public String getEdgesAsJson(EdgeGroup edgeGroup) throws JsonProcessingException {
        return objectMapper.writeValueAsString(edgeGroup.getEdges());
    }

}
