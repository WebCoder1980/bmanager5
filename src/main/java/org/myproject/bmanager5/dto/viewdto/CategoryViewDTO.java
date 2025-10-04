package org.myproject.bmanager5.dto.viewdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.myproject.bmanager5.dto.response.CategoryDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CategoryViewDTO extends CategoryDTO {
    @Setter
    private List<String> paths;
}
