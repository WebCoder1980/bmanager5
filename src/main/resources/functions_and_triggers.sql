-- functions

CREATE OR REPLACE FUNCTION category_hierarchy_recursive_refresh()
    RETURNS void AS $$
BEGIN
    TRUNCATE TABLE category_hierarchy_recursive;

    INSERT INTO category_hierarchy_recursive (child_id, parent_id)
    WITH RECURSIVE TR_RES AS (
        SELECT
            t.id AS child_id,
            t.id AS start_id
        FROM category t
                 LEFT JOIN category_hierarchy ch ON t.id = ch.parent_id

        UNION

        SELECT
            t.id AS child_id,
            tr.start_id
        FROM category t
                 JOIN category_hierarchy ch ON t.id = ch.child_id
                 JOIN TR_RES tr ON tr.child_id = ch.parent_id
    )
    SELECT child_id, start_id AS parent_id
    FROM TR_RES;
END;
$$ LANGUAGE plpgsql;

-- triggers

CREATE OR REPLACE FUNCTION category_trigger_function()
    RETURNS TRIGGER AS $$
BEGIN
    PERFORM category_hierarchy_recursive_refresh();
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS category_refresh_trigger ON category;

CREATE TRIGGER trg_category_refresh
    AFTER INSERT OR UPDATE OR DELETE
    ON category
    FOR EACH STATEMENT
EXECUTE FUNCTION category_trigger_function();

DROP TRIGGER IF EXISTS category_hierarchy_trigger ON category_hierarchy;

CREATE TRIGGER trg_category_hierarchy_refresh
    AFTER INSERT OR UPDATE OR DELETE
    ON category_hierarchy
    FOR EACH STATEMENT
EXECUTE FUNCTION category_trigger_function();