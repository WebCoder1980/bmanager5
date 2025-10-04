package org.myproject.bmanager5.dto.viewdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.myproject.bmanager5.dto.response.CategoryDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CategoryViewDTO extends CategoryDTO {
    protected String path;
}
