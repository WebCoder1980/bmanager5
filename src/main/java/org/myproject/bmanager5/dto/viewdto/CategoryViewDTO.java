package org.myproject.bmanager5.dto.viewdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.myproject.bmanager5.dto.response.CategoryDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CategoryViewDTO extends CategoryDTO {
    protected String path;
}
