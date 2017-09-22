/*
* note.js 封装与 note 有关的脚本
*/
function listNotesAction(){
	//$(this) 是 li对象, 就是被点击的li对象
	//获取显示时候绑定的序号index
	var index = $(this).data('index');
	//设置笔记本的选中的效果
	$("#notebooks li a").removeClass("checked");
	$(this).find('a').addClass("checked");
	
	var url='note/list2.do';
	var id=model.notebooks[index].id;
	model.notebookId=id;
	var param={notebookId:id};
	$.getJSON(url, param, function(result){
		if(result.state==SUCCESS){
			var notes=result.data;
			model.updateNotesView(notes);
		}else{
			alert(result.message);
		}
	});
}
//更新界面，在界面中显示全部的笔记
model.updateNotesView=function(notes){
	console.log('updataNotesView');
//	if(notes){
//		this.notes=notes;
//	}
	
	//给模型中增加新属性，存储在当前笔记列表
	this.notes=notes;
	console.log(notes);
	//遍历笔记数组，将笔记列表显示到界面
	var temp='<li class="online"><a>'+
	'<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+
	' #{title}<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button></a><div class="note_menu" tabindex="-1"><dl><dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt><dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i>'+
	'</button></dt><dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除">'+
	'<i class="fa fa-times"></i></button></dt></dl></div></li>';
	var ul=$('#notes').empty();
	for(var i=0;i<this.notes.length;i++){
		var note=this.notes[i];
		console.log(note);
		var li=$(temp.replace("#{title}",note.name));
		li.data('index', i);
		//添加选定效果
	//	console.log(model.noteIndex);
		if(model.noteIndex==i){
			li.find('a').addClass("checked");
			//console.log(model.noteIndex);
		}
		ul.append(li);
	}
}
//点击笔记显示笔记的标题  内容
function listNoteAction(){
	var index = $(this).data('index');
	//设置笔记本的选中的效果
	$("#notes li a").removeClass("checked");
	$(this).find('a').addClass("checked");
	
	//保存笔记的index到model
	model.noteIndex=index;
	
	
	var url='note/list3.do';
	var id=model.notes[index].id;
	var param={noteId:id};
	$.getJSON(url, param, function(result){
		if(result.state==SUCCESS){
			var note=result.data;
			model.updateNotessView(note);
		}else{
			alert(result.message);
		}
	});
}
//更新笔记内容
model.updateNotessView=function(note){
	console.log('updataNotessView');
	//将笔记的信息存到model中
	this.note=note;
	
	$('#input_note_title').val(this.note.title);
	//将笔记的信息显示到界面中
	if(this.note.body!=null){
	um.setContent(this.note.body);
	}
}

//保存笔记
function saveNoteAction(){
	//console.log("saveNoteAction");
	var url="note/save.do";
	var note=model.note;
	var title=$("#input_note_title").val();
	var body=um.getContent();
	console.log(title+note.title);
	if(note.title==title&&note.body==body){
		//没有修改就不需要执行下面保存方法
		return;
	}
	var param={
			noteId:model.note.id,
			title:title,
			body:body
	};
//	console.log(param);
	$('#save_note').attr("disabled","disabled").html("保存中。。");
	$.post(url,param,function(result){
		$('#save_note').removeAttr("disabled","disabled").html("保存笔记");
		if(result.state==SUCCESS){
			
			if(result.data){
				console.log(result.data);
				model.note.title=title;
				model.note.body=body;
				//更改笔记本中当前笔记的title
				model.notes[model.noteIndex].name=title;
				console.log(model.notes[model.noteIndex]);
				//跟新笔记列表区域
				model.updateNotesView(model.notes);
				
			}
		}else{
			alert(result.message);
		}
	});
}
//添加笔记
function addNoteAction(){
	var title=$("#input_note").val();
	var url="note/addnote.do";
	var uid=getCookie("userId");
	var nid=model.notebookId;
	var param={
			notebookId:nid,
			title:title,
			userId:uid
	};
	//console.log(param);
	/*
	 * body: null
		createTime: 1482044375069
		id: "fcc0bf24-453e-4a5a-95ac-7c78013dc90c"
		modifyTime: 0
		notebookId: "130077c5-284a-4520-aeb3-f18411c29309"
		statusId: null
		title: "哈哈哈哈哈"
		type: null
		user_id: "39295a3d-cc9b-42b4-b206-a2e7fab7e77c"
		__proto__: Object
	 */
	var btn=$(this).attr("disabled","disabled").html("创建中。。。。。。。");
	$.post(url,param,function(result){
		if(result.state==SUCCESS){
			btn.removeAttr("disabled").html("创建");
			var note=result.data;
			model.notes.unshift({id:note.id,name:note.title});
			model.noteIndex=0;
			model.updateNotessView(note);
			model.updateNotesView(model.notes);
			
		}else{
			alert(result.message);
		}
	});
	$('#can').empty();
	$('.opacity_bg').hide();
}
//删除笔记
function deleteNoteAction(){
	
	console.log(model.deleteNoteId);
	console.log(model.noteIndex);
	var index=model.deleteNoteId;
	var note=model.notes[index];
	var url="note/deleteNote.do";
	var param={noteId:note.id};
	$.post(url,param,function(result){
		console.log(result.state);
		if(result.state==SUCCESS){
			console.log(model.notes);
			model.notes.splice(index,1);
			model.updateNotesView(model.notes);
		}
	});
	$('#can').empty();
	$('.opacity_bg').hide();
}



