select cn_user_id from cn_notebook;

select cn_notebook_id as "id",
	   cn_notebook_name as name
from cn_notebook
where cn_user_id='03590914-a934-4da9-ba4d-b41799f917d1';