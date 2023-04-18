package com.mega.component.nuc.command;

import java.util.List;

public abstract class AbstractCommand {

    // Request.Command
    protected Command key;

    // 展示文案，已做多语言处理
    protected String name;

    // 组名
    protected String groupName;

    // 请示参数
    protected List<Parameter> requestParameters;

    // 响应参数
    protected List<Parameter> responseParameters;

    public AbstractCommand() {
    }

    public Command getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<Parameter> getRequestParameters() {
        return requestParameters;
    }

    public List<Parameter> getResponseParameters() {
        return responseParameters;
    }


    public static class Parameter {
        // Request.Parameters.Key
        private String key;

        // 展示文案，已做多语言处理
        private String name;

        // Type.FullName
        private String type;

        /// 定义域
        /// enum、bool、char、Guid：
        /// default表示不限制（若PMS不定义enum，也可按照string的规则）
        /// string：
        /// default表示纯string
        /// 数组{"A", "B", "C"}表示多选一
        /// 否则表示要用Json反解的Type.FullName
        /// sbyte、byte、short、ushort、int、uint、long、ulong、single、float、double、decimal、DateTime、Date、Time：
        /// default表示不限制
        /// 数组{1,2,3}表示多选一
        /// 范围(1,2)、[3,4]、(5,6]、[7,8)表示范围
        /// 范围(1,2),[3,4],(5,6],[7,8)表示多范围
        private String domain;

        public Parameter(String key) {
            this.key = key;
        }

        public Parameter(String key, String name) {
            this.key = key;
            this.name = name;
        }

        public Parameter(String key, String name, String type, String domain) {
            this.key = key;
            this.name = name;
            this.type = type;
            this.domain = domain;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getDomain() {
            return domain;
        }

        @Override
        public String toString() {
            return "Parameter{" +
                    "key='" + key + '\'' +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    ", domain='" + domain + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "AbstractCommand{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", groupName='" + groupName + '\'' +
                ", requestParameters=" + requestParameters +
                ", responseParameters=" + responseParameters +
                '}';
    }

}
