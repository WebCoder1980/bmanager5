package org.myproject.bmanager5.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CategoryDTO {
    protected Long id;

    protected String name;

    protected Set<Long> parentsId;

    protected Set<Long> childrenId;
}
