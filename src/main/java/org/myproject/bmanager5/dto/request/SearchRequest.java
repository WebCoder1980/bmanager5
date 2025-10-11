package org.myproject.bmanager5.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class SearchRequest {
    protected String rsqlFilter;

    protected String rsqlSort;

    protected Integer pageStart = 0;

    protected Integer pageSize = Integer.MAX_VALUE;
}
