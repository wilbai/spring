//��¼����ѧ����֤
function login_studentID_username() {
	var stuIDval = document.getElementById("studentID").value;
	var patt1 = new RegExp(/^[0-9]+$/);
	var studentCue = document.getElementById("studentCue");
	if(!patt1.test(stuIDval)){
		studentCue.innerHTML="<font color='red'><b>ѧ��ֻ��Ϊ������</b></font>";
	}
	return patt1.test(stuIDval);
}
//��¼����ְ������֤
function login_teacherID_username() {
	var stuIDval = document.getElementById("teacherID").value;
	var patt1 = new RegExp(/^[0-9]+$/);
	var teacherCue = document.getElementById("teacherCue");
	if(!patt1.test(stuIDval)){
		teacherCue.innerHTML="<font color='red'><b>ְ����ֻ��Ϊ������</b></font>";
	}
	return patt1.test(stuIDval);
}
//�޸�������������֤
function login_pwd_username() {
	var patt1 = new RegExp(/^[a-zA-Z]\w{5,17}$/);
	//��ȡ�������ֵ
	var newpasswordvalue = document.getElementById("newpassword").value;
	//��ȡȷ�������ֵ
	var confirmpasswordvalue = document.getElementById("confirmpassword").value;
	var modpwdCue = document.getElementById("modpwdCue");
	if(!patt1.test(newpasswordvalue)) {
		modpwdCue.innerHTML="<font color='red'><b>����ĸ��ͷ��������6-18֮�䣬ֻ�ܰ����ַ������ֺ��»��ߡ�</b></font>";
	}
	if(newpasswordvalue!=confirmpasswordvalue){
		modpwdCue.innerHTML="<font color='red'><b>�������벻һ�£�����������</b></font>";
	}
	return patt1.test(newpasswordvalue)&&(newpasswordvalue==confirmpasswordvalue);
}