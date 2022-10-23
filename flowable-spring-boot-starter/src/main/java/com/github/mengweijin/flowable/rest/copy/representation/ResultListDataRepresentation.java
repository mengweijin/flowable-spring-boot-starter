/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mengweijin.flowable.rest.copy.representation;

import lombok.Data;

import java.util.List;

/**
 * @author mengweijin
 */
@Data
public class ResultListDataRepresentation {

    protected Integer size;
    protected Long total;
    protected Integer start;
    protected List<? extends Object> data;

    public ResultListDataRepresentation() {
    }

    public ResultListDataRepresentation(List<? extends Object> data) {
        this.data = data;
        if (data != null) {
            size = data.size();
            total = (long) data.size();
            start = 0;
        }
    }
}
