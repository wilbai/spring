<%@ page language="java" contentType="text/html; charset=gbk"
    pageEncoding="gbk"%>
    <html>
    <body>
			<div class="list-page">
				<form id="page">
				<input type="hidden" name="type" value="${empty type?0:type }">
				<input type="hidden" name="keyword" value="${keyword }">
    <div id="pageDiv"style="margin: 0 auto;width: 500px;text-align: center;font-size: 15px; ">
    <span>��${pager.currentPage}ҳ(��${pager.totalPage}ҳ)</span><br/>
    <input type="button" value="��ҳ" style="margin-top: 4px;" id="startPage" class="btn btn6"/>
    <input type="button" value="��һҳ" style="margin-top: 4px;" id="upPage" class="btn btn6"/>
    <input type="button" value="��һҳ" style="margin-top: 4px;" id="nextPage" class="btn btn6"/>
    <input type="button" value="ĩҳ" style="margin-top: 4px;" id="endPage" class="btn btn6"/><br/>
          ��ת����<input type="text" style="width: 30px;" value="${pager.currentPage}" id="skipPage"/>ҳ 
          <input type="button" value="��ת" style="margin-top: 4px;" id="skip" class="btn btn6"/>
    </div>
    </form>
				</div>
			</body>
			</html>