package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;
import com.mega.component.nuc.command.RequestBuilderInterface;

import java.util.HashMap;
import java.util.Map;

public class GetParametersCommand extends AbstractCommand {


    public GetParametersCommand() {
        this.key = BusCommand.GetParameters;
        this.name = "获取参数";
        this.groupName = "BUS";
    }
    public static class  GetParametersBuilder implements RequestBuilderInterface<Map<String, Object>> {
        private final Map<String, Object> parameter = new HashMap<>();

        public static GetParametersBuilder create() {
            return new GetParametersBuilder();
        }

        public GetParametersBuilder addParameter(String key) {
            this.parameter.put(key, null);
            return this;
        }

        public GetParametersBuilder addParameter(AbstractCommand.Parameter key) {
            this.parameter.put(key.getKey(), null);
            return this;
        }

        @Override
        public Map<String, Object> build() {
            return this.parameter;
        }
    }
}
