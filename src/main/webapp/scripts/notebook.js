
/*
 * notebook.js封装笔记本有关的脚本
 */

function loadNotebooksAction(){
	var url="note/list7.do";
	console.log(url);
	if(!model.notebooks){
		model.pageNum=0;
	}else{
		model.pageNum++;
	}
	var data={userId:getCookie("userId"),page:model.pageNum};
	$.getJSON(url,data,function(result){
		if(result.state==SUCCESS){
			var notebooks=result.data;
			//console.log(notebooks);
			//调用模型更新笔记本列表功能
			model.updateNotebooksView(notebooks);
			//console.log('1231321'); 
		}
	});
}
model.updateNotebooksView=function(notebooks){
	/*
	 * this就是当前对象，就是model
	 * 为model增加新属性notebook；
	 * 	<li class="online">
	 *	<a  class='checked'>
	 *	<i class="fa fa-book" title="online" rel="tooltip-bottom">
	 *	</i> 默认笔记本</a></li>
	 */
	if(!this.notebooks){
		this.notebooks=notebooks;
	}else{
		//追加
		this.notebooks=this.notebooks.concat(notebooks);
	}
	//console.log(notebooks);
	var ul=$("#notebooks").empty();
	var template='<li class="online notebook">'+
	'<a><i class="fa fa-book" title="online" rel="tooltip-bottom"></i>#{name}</a></li>';
	for(var i=0;i<this.notebooks.length;i++){
//		var notebook=this.notebooks[i];
//		var li=template.replace("#{name}",notebook.name);
//		url.append(li);
		var notebook=this.notebooks[i];
		//替换模板中的 标记 #{name}
		var li=$(template.replace('#{name}',notebook.name));
		//Jquery对象提供了数据绑定方法 data
		//在每个LI元素上绑定了一个位置序号index
		li.data('index', i);
		ul.append(li);
	}
	var li='<li class="online more"><a>More....</a></li>';
	ul.append(li);

};

//添加笔记本
function addNotebookAction(){
	var name=$("#input_notebook").val();
	var url="note/add.do";
	var id=getCookie("userId");
	var param={userId:id,"name":name};
	$.post(url,param,function(result){
		//console.log("添加成功");
		loadNotebooksAction();
		console(result);
	});

	$('#can').empty();
	$('.opacity_bg').hide();

}







