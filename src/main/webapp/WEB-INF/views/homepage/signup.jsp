<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/includes/header.jsp"%>
<!DOCTYPE html>
<html>
<script>
	var teamLength;
	
	$(document).ready(function() {
		$(".teamName").keyup(function() {
			$("#vals").html($(this).val());
		});
		
		$('#urlBtn').click(function(){
			var url = $('#urlDiv input').val();
			var forms = "";
			
			 $.ajax({
				type: "get",
				url: "./cloneUrl?gitUrl="+url,
				success : function(datas) {
					$('#urlBtn').removeClass("btn-danger");
					$('#urlBtn i').removeClass("glyphicon-refresh");
					$('#urlBtn').addClass("btn-success");
					$('#urlBtn i').addClass("glyphicon-ok");
					$('#urlBtn').html("등록완료");
					$('#urlBtn').attr('disabled',true);
					$('#teamList table').append("<tbody><tr><td>Student Number</td><td>Github ID</td><td>Select Leader</td></tr></tbody>");
					for(var i=0;i<datas.length;i++){
						forms += "<tr id='teamNum"+i+"'><td><input type='text' name='stNum' placeholder='학번 입력' /></td>"
								  +"<td><input type='text' name='stName' value='"+datas[i]+"' readonly /></td>"
								  +"<td><input type='radio' name='leaderRadio' value='"+i+"' onclick='selectReader(this)'/></td></tr>";
					}
					teamLength = datas.length;
					$('#teamList table tbody').append(forms);
				},beforeSend:function(){ 
			        $('#urlBtn i').css('display','none'); 
			        $('#urlDiv img').css('display','inline-block');
			    },complete:function(){ 
			    	 $('#urlBtn i').css('display','inline-block'); 
				     $('#urlDiv img').css('display','none');
			    },error: function(jqXHR, textStatus, ex){
					alert("error");
					console.log(textStatus + "," + ex + "," + jqXHR.responseText);
				}
			}); 
			 
			
		});
		 var file = document.querySelector('#getfile');
			file.onchange = function(){
				var fileList = file.files;
				
				var reader = new FileReader();
			    reader.readAsDataURL(fileList[0]);

			    //로드 한 후
			    reader.onload = function  () {
			        document.querySelector('#preview').src = reader.result;
			    }; 
			};	
		
	});
	function selectReader(el){
		$('#teamNum'+el.value+' td input[name=stNum]').attr('name','leaderNum');
		$('#teamNum'+el.value+' td input[name=stName]').attr('name','gitName');
		for(var i=0;i<teamLength;i++){
			if(el.value != i){
				$('#teamNum'+i+' td input[name=leaderNum]').attr('name','stNum');
				$('#teamNum'+i+' td input[name=gitName]').attr('name','stName');
			}
		}
	}
</script>
<body>
	<div class="container">
		<form method="post" enctype="multipart/form-data">
		<div>
			<div class="col-lg-3 col-sm-6 text-center">
				<img class="img-circle img-responsive img-center"
					src="<c:url value="/resources/img/signup/github.png"/>" alt=""
					style="width: 200px; height: 200px"><br />
				<p style="padding-left: 5px;">Team Name</p>
				<div style="color: red" id="vals">ex)협업 관리 시스템</div>
				<br />
				<p style="padding-left: 5px;">Team Introduce</p>
				<textarea style="width:100%; height:100px;" name="intro"></textarea>
			</div>
			
			<div id="signup">
					<div id="urlDiv">
						<p>Github URL</p>
						<input type="text" class="url" name="teamURL" value="" placeholder="ex)https://github.com/dudrnxps/SLANG.git"/>
						<button type="button" class="btn btn-danger" id="urlBtn"> 
						<img src="${pageContext.request.contextPath}/resources/img/signup/loading.gif" style="display:none;"/>
						<i class="glyphicon glyphicon-refresh" aria-hidden="true"></i>&nbsp;등록</button>
					</div>
					<hr />
					
					<div id="teamList">
						<table>
						</table>
					</div>
					
					<div>
						<p style="padding-left: 5px;">Team Name</p>
						<input type="text" name="teamName" class="teamName" value=""
							placeHolder="팀 이름" id="tt">
					</div>
					<div>
						<p style="padding-left: 5px;">Password</p>
						<input type="password" name="password" />
					</div>
					<div>
						<p style="padding-left: 5px;">파일: </p>
						<input type="file" id="getfile" name="getfile" accept="image/*">
					</div>
					<br />

					<button type="submit" class="btn btn-primary" name="signup">SignUp</button>
			 </div>
		</div>
				</form>
		
	</div>
			<hr />
			<!-- Footer -->
			<%@include file="/WEB-INF/includes/footer.jsp"%>
			<!-- End Footer -->

</body>
</html>