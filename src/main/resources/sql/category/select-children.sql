SELECT c.*, p.PATH, cp.ids AS "parentsIdArray", cc.ids AS "childrenIdArray"
FROM category c

JOIN get_category_paths(:rootId) p USING (id)

LEFT JOIN (
    SELECT child_id, array_agg(parent_id) AS ids
    FROM category_hierarchy
    GROUP BY child_id
) AS cp
ON cp.child_id = c.id

LEFT JOIN (
    SELECT parent_id, array_agg(child_id) AS ids
    FROM category_hierarchy
    GROUP BY parent_id
) AS cc
ON cc.parent_id = c.id