/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dashbuilder.dataset.engine.function;

import java.util.List;
import java.util.stream.Collectors;

import org.dashbuilder.dataset.group.AggregateFunction;
import org.dashbuilder.dataset.group.AggregateFunctionType;

public class JoinFunction implements AggregateFunction {

    private static final CharSequence DEFAULT_DELIMITER = " ";
    private CharSequence delimiter;

    public JoinFunction() {
        this(DEFAULT_DELIMITER);
    }

    public JoinFunction(CharSequence delimiter) {
        super();
        this.delimiter = delimiter;
    }

    @Override
    public AggregateFunctionType getType() {
        return AggregateFunctionType.JOIN;
    }

    @Override
    public Object aggregate(List values) {
        return values.stream().collect(Collectors.joining(delimiter));
    }

    @Override
    public Object aggregate(List values, List<Integer> rows) {
        if (rows == null) {
            return aggregate(values);
        }
        if (rows.isEmpty()) {
            return 0d;
        }
        if (values == null || values.isEmpty()) {
            return 0d;
        }
        var _values = rows.stream()
                .map(values::get)
                .collect(Collectors.toList());
        return this.aggregate(_values);
    }

}
