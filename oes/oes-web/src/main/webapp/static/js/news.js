$(document).ready(function(){
	$(".ss-del-btn").click(function(){
		var a_id = $(this).attr("data-id");
		if(confirm("ȷ��ɾ������Ϣ��")){
		$.post("DoDeleteAnnouncementServlet", "a_ids=" + a_id, function(data, status){
			if(status == "success"){
				alert("ɾ���ɹ�!");
				location.reload();
			}
		});
		}
	});
});