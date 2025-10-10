package org.myproject.bmanager5.dto.response;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.myproject.bmanager5.model.CategoryModel;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class CategoryDTO extends CategoryModel {
    protected Set<Long> parentsId;

    protected Set<Long> childrenId;
}
