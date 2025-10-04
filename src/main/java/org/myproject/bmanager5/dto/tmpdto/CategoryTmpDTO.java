package org.myproject.bmanager5.dto.tmpdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.myproject.bmanager5.dto.viewdto.CategoryViewDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CategoryTmpDTO extends CategoryViewDTO {
    protected Long[] parentsIdArray;
    protected Long[] childrenIdArray;
}
