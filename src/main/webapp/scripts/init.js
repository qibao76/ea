/*
 *初始化脚本 
 */
var SUCCESS=0;
var ERROR=1;

//页面中的数据模型，存储页面中显示的数据，
//以及封装更新视图的方法；
var model={};


$(function(){
	console.log('init ok');
	//加载笔记本列表信息
	loadNotebooksAction();
	
	//监听笔记本的点击事件
	// .on(事件名称, 事件源选择器, 事件方法) 点击笔记本显示笔记
	$('#notebooks').on('click', '.notebook',listNotesAction);
	//在note.js 中定义 listNotesAction 点击笔记显示笔记的标题  内容
	$('#notes').on('click', 'li',listNoteAction);
	//在note.js中定义saveNoteAction方法 保存笔记
	$("#save_note").click(saveNoteAction);
	
	//more
	$('#notebooks').on('click', '.more',loadNotebooksAction);
	
	//监听关闭窗口事件
	$("#can").on("click",".close,.cancle",closeAction);
	//添加笔记本对话框
	$("#add_notebook").click(showNewNotebookDialogAction);
	
	//添加笔记对话框
	$("#add_note").click(showNoteDialogAction);
	
	//删除笔记对话框
	$("#notes").on("click",".btn_slide_down",showNoteMenuAction);
	$('body').click(function(){
		$('#notes .note_menu').hide();
	})
	//监听哪个按钮被点击了
	$("#notes").on("click",".btn_delete",showDeleteNote);
	
	
	
	
	
});
//删除对话框
function showDeleteNote(){
	$('#can').load("alert/alert_delete_note.html",function(){
		$('.opacity_bg').show();
		$("#can .sure").click(deleteNoteAction);
	});
}

//显示删除笔记菜单
function showNoteMenuAction(){
	var a= $(this).parent();
	var li=a.parent();
//	console.log(li);
	var index =li.data('index');
//	console.log(index+"////////////////////////////////////");
	model.deleteNoteId=index;
	//显示选项
	$(this).parent().next().toggle();
	return false;
}


//添加笔记对话框
function showNoteDialogAction(){
	$('#can').load("alert/alert_note.html",function(){
		$('.opacity_bg').show();
		$("#can .sure").click(addNoteAction);
	});
}

//添加笔记本对话框
function showNewNotebookDialogAction(){
	$('#can').load("alert/alert_notebook.html",function(){
		$('.opacity_bg').show();
		$("#can .sure").click(addNotebookAction);
	});
}
//监听关闭窗口事件
function closeAction(){
	$('#can').empty();
	$('.opacity_bg').hide();
}

