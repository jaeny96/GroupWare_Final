//결재선 관련 내용 담기는 div 객체
var cardApLineDiv;
//기안 문서 상세 내용 담기는 div 객체
var postDetailDiv;

//div 객체 만드는 함수
function createDivObj(attrClassName) {
  var div = document.createElement("div");
  if (attrClassName != null && attrClassName != "") {
    div.setAttribute("class", attrClassName);
  }
  return div;
}
//table 객체 만드는 함수
function createTableObj(attrClassName) {
  var table = document.createElement("table");
  if (attrClassName != null && attrClassName != "") {
    table.setAttribute("class", attrClassName);
  }
  return table;
}
//tbody 객체 만드는 함수
function createTBodyObj() {
  var tbody = document.createElement("tbody");
  return tbody;
}
//tr 객체 만드는 함수
function createTrObj() {
  var tr = document.createElement("tr");
  return tr;
}
//td 객체 만드는 함수
function createTdObj() {
  var td = document.createElement("td");
  return td;
}
//label 객체 만드는 함수
function createLabel(attrClassName) {
  var label = document.createElement("label");
  if (attrClassName != null && attrClassName != "") {
    label.setAttribute("class", attrClassName);
  }
  return label;
}
//input 객체 만드는 함수
function createInput(attrClassName) {
  var input = document.createElement("input");
  if (attrClassName != null && attrClassName != "") {
    input.setAttribute("class", attrClassName);
  }
  return input;
}
//button 객체 만드는 함수
function createButton(attrClassName) {
  var button = document.createElement("button");
  if (attrClassName != null && attrClassName != "") {
    button.setAttribute("class", attrClassName);
  }
  return button;
}
//summernote textarea 만드는 함수
function createSummerNote() {
  var textarea = document.createElement("textarea");
  textarea.setAttribute("id", "summernote");
  textarea.setAttribute("name", "editordata");

  return textarea;
}
//p 객체 만드는 함수
function createPObj(attrClassName) {
  var p = document.createElement("p");
  if (attrClassName != null && attrClassName != "") {
    p.setAttribute("class", attrClassName);
  }
  return p;
}

//target 객체 제거하는 함수
function emptyObj(target) {
  target.remove();
}

//결재선 card 부분 내용 채우는 함수
function createApLine(target) {
  cardApLineDiv = createDivObj();
  cardApLineDiv.setAttribute("class", "card");

  createApLineTable(cardApLineDiv);
  createAgReTable(cardApLineDiv);

  target.append(cardApLineDiv);
}

//결재 테이블 create
function createApLineTable(target) {
  var apTable = createTableObj("table table-bordered");

  var apTbody = createTBodyObj();

  //결재선 테이블 내 tr/td create
  for (var i = 0; i < 3; i++) {
    var tr = createTrObj();
    if (i == 0) {
      var tdTitle = createTdObj();
      tdTitle.setAttribute("rowspan", "3");
      tdTitle.setAttribute("class", "font-weight-bolder text-center");
      tdTitle.setAttribute("style", "width: 12%; background-color: #f6f6f6");
      tr.appendChild(tdTitle);
    }
    for (var j = 0; j < 4; j++) {
      var td = createTdObj();
      td.setAttribute("style", "width: 22%");
      td.setAttribute("class", "text-center");
      if (i == 0) {
        td.innerHTML = j;
      } else {
        td.classList.add("font-weight-bolder");
      }

      if (i == 1) {
        td.setAttribute("id", "apApprovalStep" + i);
      }

      if (i == 2) {
        td.classList.add("apApprovalStepName" + i);
      }

      tr.appendChild(td);
    }
    apTbody.appendChild(tr);
  }
  apTable.appendChild(apTbody);
  target.appendChild(apTable);
}

//합의 참조 테이블 create
function createAgReTable(target) {
  var agReTable = createTableObj("table table-bordered");

  var agReTbody = createTBodyObj();

  var tdClass = ["apAgreementName", "apReferenceName"];
  var tdContent = ["합의", "참조"];
  //합의 참조 테이블 내 tr/td create
  for (var i = 0; i < 2; i++) {
    var tr = createTrObj();
    for (var j = 0; j < 2; j++) {
      var td = createTdObj();
      td.setAttribute("class", "font-weight-bolder");
      if (j == 0) {
        td.classList.add("text-center");
        td.setAttribute("style", "width: 12%; background-color: #f6f6f6");
        td.innerHTML = tdContent[i];
      } else {
        td.classList.add(tdClass[i]);
        td.setAttribute("style", "width: 88%");
      }
      tr.appendChild(td);
    }
    agReTbody.appendChild(tr);
  }
  agReTable.appendChild(agReTbody);
  target.appendChild(agReTable);
}

//기본 템플릿
function standatdTemplate(target, type) {
  postDetailDiv = createDivObj();
  postDetailDiv.setAttribute("class", "mb-3 col-md-12");

  var labelTitle = createLabel("form-label font-weight-bolder");
  labelTitle.setAttribute("for", "inputWriter");
  labelTitle.setAttribute("style", "margin-right: 20px");
  labelTitle.innerHTML = "제목";

  var inputTitle = createInput("form-control");
  inputTitle.setAttribute("name", "apdocstitle");
  inputTitle.setAttribute("type", "text");
  inputTitle.setAttribute("id", "inputtitle");
  inputTitle.setAttribute("rows", "1");
  inputTitle.setAttribute("style", "border: 1px solid #ced4da");

  var labelContent = createLabel("form-label font-weight-bolder");
  labelContent.setAttribute("for", "ocument_select");
  labelContent.setAttribute("style", "margin-top: 20px");
  labelContent.innerHTML = "<br />상세 입력";

  var summernote = createSummerNote();

  var fileBoxDiv = createDivObj();
  fileBoxDiv.setAttribute("class", "filebox");
  fileBoxDiv.setAttribute("style", "margin-top: 10px");

  var fileLabel = createLabel();
  fileLabel.setAttribute("for", "file");
  fileLabel.innerHTML = "업로드";

  var fileInput = createInput();
  fileInput.setAttribute("type", "file");
  fileInput.setAttribute("id", "file");

  var uploadInput = createInput("upload-name");
  uploadInput.setAttribute("value", "파일선택");

  fileBoxDiv.appendChild(fileLabel);
  fileBoxDiv.appendChild(fileInput);
  fileBoxDiv.appendChild(uploadInput);

  var draftBtn = createButton(
    "btn btn-outline-danger float-right btn-approvalsetting"
  );
  draftBtn.setAttribute("type", "button");
  draftBtn.setAttribute("id", "draftComplete");
  draftBtn.setAttribute("style", "margin-bottom: 20px");

  draftBtn.innerHTML =
    "<i class='fas fa-check' style='padding: 10px'></i>기안하기";

  postDetailDiv.appendChild(labelTitle);
  postDetailDiv.appendChild(inputTitle);
  postDetailDiv.appendChild(labelContent);
  postDetailDiv.appendChild(summernote);
  postDetailDiv.appendChild(fileBoxDiv);
  postDetailDiv.appendChild(draftBtn);
  target.append(postDetailDiv);
}

//지출결의서
function SRTemplate(target) {
  var table = createTableObj("tableType spending");
  table.innerHTML =
    "<colgroup>" +
    "<col style='width: 12.09%;'>" +
    "<col style='width: 87.91%;'>" +
    "</colgroup>" +
    "<tbody>" +
    "<tr>" +
    "<th scope='row'>구분</th>" +
    "<td>" +
    "<label>" +
    "<input type='radio' name='spending_type' value='P' checked >" +
    "개인" +
    "</label>" +
    "<label>" +
    "<input type='radio' name='spending_type' value='C' >" +
    "법인" +
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row' id='th_spending_regist_month'>회계 기준월</th>" +
    "<th scope='row' style='display: none;' id='th_spending_regist_days'>회계 기준일" +
    "</th>" +
    "<td>" +
    "<div class='to-item' id='spending_regist_md'>" +
    "<select class='write-select' style='width: 80px;' id='selectFixedYear'>" +
    "<option value='2016'>2016</option>" +
    "<option value='2017'>2017</option>" +
    "<option value='2018'>2018</opti'n>" +
    "<option value='2019'>2019</option> " +
    "<option value='2020'>2020</option>" +
    "<option value='2021' selected>2021</option> " +
    "</select>" +
    "년" +
    "<select class='write-select' style='width: 50px;' id='selectFixedMonth'>" +
    "<option value='1'>1</option>" +
    "<option value='2'>2</option>" +
    "<option value='3'>3</option>" +
    "<option value='4'>4</option>" +
    "<option value='5'>5</option>" +
    "<option value='6'>6</option>" +
    "<option value='7' selected=''>7</option>" +
    "<option value='8'>8</option>" +
    "<option value='9'>9</option>" +
    "<option value='10'>10</option>" +
    "<option value='11'>11</option>" +
    "<option value='12'>12</option>" +
    "</select>" +
    "월" +
    "<select class='write-select' style='width: 120px; display:none;' id='selectFixedDays'>" +
    "<option value='0'>회계 기준일</option>" +
    "</select>" +
    "</div>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>지출자</th>" +
    "<td>" +
    "<input type='text' class='spending-add' placeholder='클릭 후 입력' id='inputSpenderName'> " +
    "<span class='hide' id='textSpenderName'></span>" +
    "</td>" +
    "</tr>" +
    "<tr id='infoEmployeeAccount' style='display: table-row;'>" +
    "<th scope='row'>계좌 정보</th>" +
    "<td>" +
    "<input type='text' class='accountNo' id='accountNoId'>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>거래 내역</th>" +
    "<td id='approvalFirstLine'>" +
    "<textarea class='spendingDetailContents' id='inputSpendingDetail' rows='8' cols='80' style='box-sizing:border-box; resize:none; padding: 2px;'>" +
    "</textarea>" +
    "</td>" +
    "</tr>" +
    "</tbody>";

  target.prepend(table);
}
//회람
function LETemplate(target) {
  var p = createPObj();
  p.innerHTML = "<h3 style='font-weight:bold'>휴가계</h3>";

  var table = createTableObj("tableForLeave");
  table.innerHTML =
    "<colgroup>" +
    "<col width='100'>" +
    "<col>" +
    "</colgroup>" +
    "<tbody>" +
    "<tr>" +
    "<th scope='row'>소속팀</th>" +
    "<td>" +
    "<label>" +
    "<input type='text' id='inputTeam' maxlength='20'>" +
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>직위</th>" +
    "<td>" +
    "<label>" +
    "<input type='text' id='inputPosition' maxlength='20'>" +
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>성명</th>" +
    "<td>" +
    "<label>" +
    "<input type='text' id='inputFullName' width='115px'>" +
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>생년월일</th>" +
    "<td>" +
    "<label>" +
    "<input type='text' id='inputBirthInfo' width='115px'>" +
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>연락처</th>" +
    "<td>" +
    "<label>" +
    "<input type='text' id='inputContactNum' width='115px'>" +
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th>현주소</th>" +
    "<td colspan='3'>" +
    "<label>" +
    "<input type='text' class='address' id='inputAddress' style='width: 320px; display: inline-block;' autocomplete='off'>" +
    "</label> " +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th>휴가기간</th>" +
    "<td>" +
    "<label>" +
    "<input type='text' class='freeDays' id='inputYourWish' placeholder='20__년 _월 _일 - 20__년 _월 _일 형식으로 기재' " +
    "style='width: 320px; display: inline-block;' autocomplete='off'>" +
    "</label> " +
    "</td>  " +
    "</tr>" +
    "<tr>" +
    "<th>휴가사유</th>" +
    "<td>" +
    "<textarea class='leaveReason' id='inputLeaveReason' cols='100' style='box-sizing:border-box; resize:none; padding: 2px;'></textarea>" +
    "</td>" +
    "<td>" +
    "</td>" +
    "</tr>" +
    "</tbody>";
  target.prepend(table);
  target.prepend(p);
}

//품의서
function ACTemplate(target) {
  var table = createTableObj("tableForLeave");
  table.innerHTML =
    "<colgroup>" +
    "<col width='120'>" +
    "<col>" +
    "</colgroup>" +
    "<tbody>" +
    "<tr>" +
    "<th scope='row'>거래처</th>" +
    "<td colspan='3'>" +
    "<label>" +
    "<input type='text' class='account-add' placeholder='클릭 후 입력' id='inputRemittanceCustomer' style='width: 320px;' autocomplete='off'>" +
    "<input type='text'class='account-add hide' placeholder='' id='inputCustomer' style='width: 320px'>" +
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>은행명</th>" +
    "<td colspan='3'>" +
    "<select class='selectBank' id='selectBankName'>" +
    "<option value>은행선택</option>" +
    "<option value='국민은행'>국민은행</option>" +
    "<option value='신한은행'>신한은행</option>" +
    "<option value='우리은행'>우리은행</option>" +
    "<option value='KEB하나은행'>KEB하나은행</option>" +
    "<option value='KDB은행'>KDB산업은행</option>" +
    "<option value='IBK기업은행'>IBK기업은행</option>" +
    "<option value='NH농협은행'>NH농협은행</option>  " +
    "<option value='수협은행'>수협은행</option>   " +
    "<option value='우체국'>우체국</option>  " +
    "<option value='SELF'>직접입력</option>  " +
    "</select>" +
    "<label>" +
    "<input type='text' id='inputBankName' maxlength='20' style='display: inline-block;'>" +
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>계좌번호</th>" +
    "<td>" +
    "<label>" +
    "<input type='text' id='inputAccountNum' maxlength='20'>" +
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>예금주</th>" +
    "<td>" +
    "<label>" +
    "<input type='text' id='inputAccountHolder' maxlength='20'>" +
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>송금액</th>" +
    "<td>" +
    "<label>" +
    "<input type='text' id='inputPrice'>" +
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>송금요청일</th>" +
    "<td>" +
    "<label>" +
    "<input type='text' id='inputRequestDate' style='width:115px;' readonly='' class='hasDatepicker'> " +
    "</label>" +
    "</td>" +
    "</tr>" +
    "</tbody>";
  target.prepend(table);
}

$(function () {
  //문서 종류 select 객체
  var $selectObj = $("#documentTypeSelect");
  //결재선 관련 card-body 객체
  var $apLineCardObj = $("div.apLineCard");
  //결재선 설정 버튼 객체
  var $apLineBtnObj = $("button#approvalSetting");

  //상세 내용 관련 card-header 객체
  var $postDetailCardHeaderObj = $("div.postDetailCardHeader");
  //상세 내용 관련 card-body 객체
  var $postDetailCardObj = $("div.apPostDetail");

  //선택 option에서 설정된 설명 객체
  //ex : 문서 종류 선택 시 결재선이 노출됩니다~
  var $apLineExplain = $("h5.text-muted");

  $selectObj.on("change", function (e) {
    var templateType = $(this).val();
    localStorage.setItem("templateType", templateType);
    //option value가 '선택'이 아닐때
    if (templateType != "#") {
      $apLineBtnObj.attr("style", "");
      $apLineExplain.attr("style", "display:none");
      $postDetailCardHeaderObj.attr("style", "display:none");
      if (cardApLineDiv != null) {
        emptyObj(cardApLineDiv);
      }
      if (postDetailDiv != null) {
        emptyObj(postDetailDiv);
      }
      createApLine($apLineCardObj);
      standatdTemplate($postDetailCardObj, templateType);
      $("#summernote").summernote({
        height: 600, // 에디터 높이
        minHeight: null, // 최소 높이
        maxHeight: null, // 최대 높이
        focus: true, // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR", // 한글 설정
      });
      if (templateType == "SR") {
        SRTemplate($("div.note-editing-area"));
      } else if (templateType == "LE") {
        LETemplate($("div.note-editing-area"));
      } else if (templateType == "AC") {
        ACTemplate($("div.note-editing-area"));
      }
    } else {
      //'선택'일때
      $apLineBtnObj.attr("style", "display: none");
      $apLineExplain.attr("style", "");
      $postDetailCardHeaderObj.attr("style", "");
      if (cardApLineDiv != null) {
        emptyObj(cardApLineDiv);
      }
      if (postDetailDiv != null) {
        emptyObj(postDetailDiv);
      }
    }
  });
});
