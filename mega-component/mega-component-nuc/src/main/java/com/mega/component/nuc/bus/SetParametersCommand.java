package com.mega.component.nuc.bus;

import com.mega.component.nuc.command.AbstractCommand;
import com.mega.component.nuc.command.BusCommand;
import com.mega.component.nuc.command.RequestBuilderInterface;

import java.util.HashMap;
import java.util.Map;

public class SetParametersCommand extends AbstractCommand {

    public SetParametersCommand() {
        this.key = BusCommand.SetParameters;
        this.name = "设置参数";
        this.groupName = "BUS";
    }

    public static class SetParametersBuilder implements RequestBuilderInterface<Map<String, Object>> {
        private final Map<String, Object> parameter = new HashMap<>();

        public static SetParametersBuilder create() {
            return new SetParametersBuilder();
        }

        public SetParametersBuilder addParameter(String key, Object value) {
            this.parameter.put(key, value);
            return this;
        }

        public SetParametersBuilder addParameter(AbstractCommand.Parameter key, Object value) {
            this.parameter.put(key.getKey(), value);
            return this;
        }

        @Override
        public Map<String, Object> build() {
            return this.parameter;
        }
    }
}
