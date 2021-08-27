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
      tdTitle.innerHTML = "결재";
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
        td.setAttribute("id", "apApprovalStep" + j);
      }

      if (i == 2) {
        td.classList.add("apApprovalStepName" + j);
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

var html1;
var html2;
var html3;
var html4;
var html5;
var html6;
var html7;
var html8;
var html9;
var html10;
var html11;
var html12;
var html13;
var html14;
var html15;
var html16;
var html17;

//지출결의서
function SRTemplate(target) {
  var table = createTableObj();
  html1 =
    "<colgroup>" +
    "<col style='width: 12.09%;'>" +
    "<col style='width: 87.91%;'>" +
    "</colgroup>" +
    "<tbody>" +
    "<tr>" +
    "<th scope='row'>구분</th>" +
    "<td>";
  html2 =
    "<label>" +
    "<input type='radio' name='spending_type' value='P' checked >" +
    "개인" +
    "</label>" +
    "<label>" +
    "<input type='radio' name='spending_type' value='C' >" +
    "법인" +
    "</label>";
  html3 =
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row' id='th_spending_regist_month'>회계 기준월</th>" +
    "<th scope='row' style='display: none;' id='th_spending_regist_days'>회계 기준일" +
    "</th>" +
    "<td>" +
    "<div class='to-item' id='spending_regist_md'>";
  html4 =
    "<select class='write-select' style='width: 80px;' id='selectFixedYear'>" +
    "<option value='2016'>2016</option>" +
    "<option value='2017'>2017</option>" +
    "<option value='2018'>2018</opti'n>" +
    "<option value='2019'>2019</option> " +
    "<option value='2020'>2020</option>" +
    "<option value='2021' selected>2021</option> " +
    "</select>" +
    "년";
  html5 =
    "<select class='write-select' style='width: 50px;' id='selectFixedMonth'>" +
    "<option value='1'>1</option>" +
    "<option value='2'>2</option>" +
    "<option value='3'>3</option>" +
    "<option value='4'>4</option>" +
    "<option value='5'>5</option>" +
    "<option value='6'>6</option>" +
    "<option value='7'>7</option>" +
    "<option value='8' selected>8</option>" +
    "<option value='9'>9</option>" +
    "<option value='10'>10</option>" +
    "<option value='11'>11</option>" +
    "<option value='12'>12</option>" +
    "</select>" +
    "월";
  html6 =
    "</div>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>지출자</th>" +
    "<td>";
  html7 =
    "<input type='text' class='spending-add' placeholder='클릭 후 입력' id='inputSpenderName'> ";
  html8 =
    "<span class='hide' id='textSpenderName'></span>" +
    "</td>" +
    "</tr>" +
    "<tr id='infoEmployeeAccount' style='display: table-row;'>" +
    "<th scope='row'>계좌 정보</th>" +
    "<td>";
  html9 = "<input type='text' class='accountNo' id='accountNoId'>";
  html10 =
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>거래 내역</th>" +
    "<td id='approvalFirstLine'>";
  html11 =
    "<textarea class='spendingDetailContents' id='inputSpendingDetail' rows='8' cols='80' style='box-sizing:border-box; resize:none; padding: 2px;'>" +
    "</textarea>";
  html12 = "</td>" + "</tr>" + "</tbody></table>";
  table.innerHTML =
    html1 +
    html2 +
    html3 +
    html4 +
    html5 +
    html6 +
    html7 +
    html8 +
    html9 +
    html10 +
    html11 +
    html12;

  target.prepend(table);
}

//휴가
function LETemplate(target) {
  var p = createPObj();
  p.innerHTML = "<h3 style='font-weight:bold'>휴가계</h3>";

  var table = createTableObj();
  html1 =
    "<colgroup>" +
    "<col width='100'>" +
    "<col>" +
    "</colgroup>" +
    "<tbody>" +
    "<tr>" +
    "<th scope='row'>소속팀</th>" +
    "<td>" +
    "<label>";
  html2 = "<input type='text' id='inputTeam' maxlength='20'>";
  html3 =
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>직위</th>" +
    "<td>" +
    "<label>";
  html4 = "<input type='text' id='inputPosition' maxlength='20'>";
  html5 =
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>성명</th>" +
    "<td>" +
    "<label>";
  html6 = "<input type='text' id='inputFullName' width='115px'>";
  html7 =
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>생년월일</th>" +
    "<td>" +
    "<label>";
  html8 = "<input type='text' id='inputBirthInfo' width='115px'>";
  html9 =
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>연락처</th>" +
    "<td>" +
    "<label>";
  html10 = "<input type='text' id='inputContactNum' width='115px'>";
  html11 =
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th>현주소</th>" +
    "<td colspan='3'>" +
    "<label>";
  html12 =
    "<input type='text' class='address' id='inputAddress' style='width: 320px; display: inline-block;' autocomplete='off'>";
  html13 =
    "</label> " +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th>휴가기간</th>" +
    "<td>" +
    "<label>";
  html14 =
    "<input type='text' class='freeDays' id='inputYourWish' placeholder='20__년 _월 _일 - 20__년 _월 _일 형식으로 기재' " +
    "style='width: 320px; display: inline-block;' autocomplete='off'>";
  html15 =
    "</label> " + "</td>  " + "</tr>" + "<tr>" + "<th>휴가사유</th>" + "<td>";
  html16 =
    "<textarea class='leaveReason' id='inputLeaveReason' cols='100' style='box-sizing:border-box; resize:none; padding: 2px;'></textarea>";
  html17 = "</td>" + "<td>" + "</td>" + "</tr>" + "</tbody></table>";
  table.innerHTML =
    html1 +
    html2 +
    html3 +
    html4 +
    html5 +
    html6 +
    html7 +
    html8 +
    html9 +
    html10 +
    html11 +
    html12 +
    html13 +
    html14 +
    html15 +
    html16 +
    html17;

  target.prepend(table);
  target.prepend(p);
}

//품의서
function ACTemplate(target) {
  var table = createTableObj();
  html1 =
    "<colgroup>" +
    "<col width='120'>" +
    "<col>" +
    "</colgroup>" +
    "<tbody>" +
    "<tr>" +
    "<th scope='row'>거래처</th>" +
    "<td colspan='3'>" +
    "<label>";
  html2 =
    "<input type='text' class='account-add' placeholder='클릭 후 입력' id='inputRemittanceCustomer' style='width: 320px;' autocomplete='off'>" +
    "<input type='text'class='account-add hide' placeholder='' id='inputCustomer' style='width: 320px'>";
  html3 =
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>은행명</th>" +
    "<td colspan='3'>";
  html4 =
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
    "<label>";
  html5 =
    "<input type='text' id='inputBankName' maxlength='20' style='display: inline-block;'>";
  html6 =
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>계좌번호</th>" +
    "<td>" +
    "<label>";
  html7 =
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>예금주</th>" +
    "<td>" +
    "<label>";
  html8 = "<input type='text' id='inputAccountHolder' maxlength='20'>";
  html9 =
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>송금액</th>" +
    "<td>" +
    "<label>";
  html10 = "<input type='text' id='inputPrice'>";
  html11 =
    "</label>" +
    "</td>" +
    "</tr>" +
    "<tr>" +
    "<th scope='row'>송금요청일</th>" +
    "<td>" +
    "<label>";
  html12 =
    "<input type='text' id='inputRequestDate' style='width:115px;' class='hasDatepicker'> ";
  html13 = "</label>" + "</td>" + "</tr>" + "</tbody></table>";
  table.innerHTML =
    html1 +
    html2 +
    html3 +
    html4 +
    html5 +
    html6 +
    html7 +
    html8 +
    html9 +
    html10 +
    html11 +
    html12 +
    html13 +
    html14;
  target.prepend(table);
}

function insertApLineTable() {
  var loginInfoIdObj = document.querySelector(
    "div.profileDropdown span.loginId"
  );
  //모달창 객체 가져옴
  var modalApLineSettingObj = document.querySelector(
    "div#modalApprovalSetting"
  );
  //모달창 내 결재 테이블 객체 가져옴
  var modalApTableObj = modalApLineSettingObj.querySelector(
    "table#approvalNameList"
  );
  //결재선 0번에 관한 tr과 td 객체 가져옴
  var modalApZeroObj = modalApTableObj.querySelector("tbody>tr:first-child");
  var modalApZeroNameObj = modalApZeroObj.querySelector("td:last-child");
  //결재선 1번에 관한 tr과 td 객체 가져옴
  var modalApFirstObj = modalApTableObj.querySelector("tbody>tr:nth-child(2)");
  var modalApFirstNameObj = modalApFirstObj.querySelector("td:last-child");
  //결재선 2번에 관한 tr과 td 객체 가져옴
  var modalApSecondObj = modalApTableObj.querySelector("tbody>tr:nth-child(3)");
  var modalApSecondNameObj = modalApSecondObj.querySelector("td:last-child");
  //결재선 3번에 관한 tr과 td 객체 가져옴
  var modalApThirdObj = modalApTableObj.querySelector("tbody>tr:last-child");
  var modalApThirdNameObj = modalApThirdObj.querySelector("td:last-child");

  //모달창 내 합의 div객체 및 버튼 객체 가져옴
  var modalAgObj = document.querySelector("div#agreementBox");
  var modalAgNameObj = modalAgObj.querySelector("button#agreementBoxBtn");
  //모달창 내 참조 div객체 및 버튼 객체 가져옴
  var modalReObj = document.querySelector("div#referenceBox");
  var modalReNameObj = modalReObj.querySelector("button#referenceBoxBtn");
  //모달 결재선 저장 버튼
  var apSetApLineSaveBtnObj = document.querySelector("button#apSettingSave");

  var docApZeroNameObj = document.querySelector("td.apApprovalStepName0");
  var docApFirstNameObj = document.querySelector("td.apApprovalStepName1");
  var docApSecondNameObj = document.querySelector("td.apApprovalStepName2");
  var docApThirdNameObj = document.querySelector("td.apApprovalStepName3");
  var docAgNameObj = document.querySelector("td.apAgreementName");
  var docReNameObj = document.querySelector("td.apReferenceName");

  function modalSetApLineSubmitHandler() {
    modalApLineSettingObj.classList.add("hidden");
    docApZeroNameObj.innerText = "";
    docApFirstNameObj.innerText = "";
    docApSecondNameObj.innerText = "";
    docApThirdNameObj.innerText = "";
    docAgNameObj.innerText = "";
    docReNameObj.innerText = "";

    docApZeroNameObj.innerText = modalApZeroNameObj.innerText;
    docApZeroNameObj.setAttribute("id", loginInfoIdObj.innerText);
    docApFirstNameObj.innerText = modalApFirstNameObj.innerText;
    docApFirstNameObj.setAttribute(
      "id",
      modalApFirstNameObj.getAttribute("id")
    );
    docApSecondNameObj.innerText = modalApSecondNameObj.innerText;
    docApSecondNameObj.setAttribute(
      "id",
      modalApSecondNameObj.getAttribute("id")
    );
    docApThirdNameObj.innerText = modalApThirdNameObj.innerText;
    docApThirdNameObj.setAttribute(
      "id",
      modalApThirdNameObj.getAttribute("id")
    );
    docAgNameObj.innerText = modalAgNameObj.innerText;
    docAgNameObj.setAttribute("id", modalAgNameObj.getAttribute("id"));
    docReNameObj.innerText = modalReNameObj.innerText;
    docReNameObj.setAttribute("id", modalReNameObj.getAttribute("id"));
  }
  apSetApLineSaveBtnObj.addEventListener("click", modalSetApLineSubmitHandler);
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
        lang: "ko-KR", // 한글 설정
      });
      if (templateType == "SR") {
        SRTemplate($("div.note-editing-area"));
      } else if (templateType == "LE") {
        LETemplate($("div.note-editing-area"));
      } else if (templateType == "AC") {
        ACTemplate($("div.note-editing-area"));
        $("select#selectBankName").on("change", function (e) {});
      }
      insertApLineTable();
      draft();
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

//----기안 관련 js 시작----
function draft() {
  var loginInfoIdObj = document.querySelector(
    "div.profileDropdown span.loginId"
  );
  var loginInfoNameObj = document.querySelector(
    "div.profileDropdown span.loginName"
  );
  //기안
  var $apBtn = $("button#draftComplete");
  //결재문서 종류
  var apDocsTypeObj = $("select#documentTypeSelect option:selected").html();
  //결재문서 제목
  var apDocsTitleObj = document.querySelector("input#inputtitle");
  //결재문서 내용
  var apDocsContentObj = document.querySelector("div.note-editable");
  //0단계 결재자 이름칸
  var apZeroStepName = document.querySelector("td.apApprovalStepName0");
  //1단계 결재자 이름칸
  var apOneStepName = document.querySelector("td.apApprovalStepName1");
  //2단계 결재자 이름칸
  var apTwoStepName = document.querySelector("td.apApprovalStepName2");
  //3단계 결재자 이름칸
  var apThreeStepName = document.querySelector("td.apApprovalStepName3");
  //합의자 이름칸
  var apAgreementStepName = document.querySelector("td.apAgreementName");
  //참조자 이름칸
  var apReferenceStepName = document.querySelector("td.apReferenceName");

  var aglineEmpId;
  var relineEmpId;

  var backurlApAddDocs = "http://localhost:8888/gwback/approval/draft/";

  function docFirst() {
    var type = localStorage.getItem("templateType");
    var content;
    if (type == "SR") {
      var html0 = "<table class='tableForSR' id='draftContentTable'>";
      var spendingType;
      if ($("input[name='spending_type']:checked").val() == "P") {
        spendingType = "개인";
      } else {
        spendingType = "법인";
      }
      html2 = "<span>" + spendingType + "</span>";
      html4 =
        "<span>" +
        $("select#selectFixedYear option:selected").val() +
        "년 </span>";
      html5 =
        "<span>" +
        $("select#selectFixedMonth option:selected").val() +
        "월 </span>";
      html7 = "<span>" + $("input#inputSpenderName").val() + "</span>";
      html9 = "<span>" + $("input#accountNoId").val() + "</span>";
      html11 = "<span>" + $("textarea#inputSpendingDetail").val() + "</span>";
      content =
        html0 +
        html1 +
        html2 +
        html3 +
        html4 +
        html5 +
        html6 +
        html7 +
        html8 +
        html9 +
        html10 +
        html11 +
        html12 +
        "</table>";
    } else if (type == "LE") {
      var html0 = "<table class='tableForLE' id='draftContentTable'>";
      html2 = "<span>" + $("input#inputTeam").val() + "</span>";
      html4 = "<span>" + $("input#inputPosition").val() + "</span>";
      html6 = "<span>" + $("input#inputFullName").val() + "</span>";
      html8 = "<span>" + $("input#inputBirthInfo").val() + "</span>";
      html10 = "<span>" + $("input#inputContactNum").val() + "</span>";
      html12 = "<span>" + $("input#inputAddress").val() + "</span>";
      html14 = "<span>" + $("input#inputYourWish").val() + "</span>";
      html16 = "<span>" + $("textarea#inputLeaveReason").val() + "</span>";
      content =
        html0 +
        html1 +
        html2 +
        html3 +
        html4 +
        html5 +
        html6 +
        html7 +
        html8 +
        html9 +
        html10 +
        html11 +
        html12 +
        html13 +
        html14 +
        html15 +
        html16 +
        html17;
      ("</table>");
    } else if (type == "AC") {
      var html0 = "<table class='tableForAC' id='draftContentTable'>";
      html2 =
        "<span>" +
        $("input#inputRemittanceCustomer").val() +
        "</span><span style='margin-left:5px'>" +
        $("input#inputCustomer").val() +
        "</span>";
      html4 =
        "<span>" + $("select#selectBankName option:selected").val() + "</span>";
      html5 =
        "<span style='margin-left:5px'>" +
        $("input#inputBankName").val() +
        "</span>";
      html8 = "<span>" + $("input#inputAccountHolder").val() + "</span>";
      html10 = "<span>" + $("input#inputPrice").val() + "</span>";
      html12 = "<span>" + $("input#inputRequestDate").val() + "</span>";
      content =
        html0 +
        html1 +
        html2 +
        html3 +
        html4 +
        html5 +
        html6 +
        html7 +
        html8 +
        html9 +
        html10 +
        html11 +
        html12 +
        html13 +
        "</table>";
    } else {
      content = apDocsContentObj.innerHTML.trim();
    }
    var apLineEmpId = [
      apZeroStepName.getAttribute("id"),
      apOneStepName.getAttribute("id"),
      apTwoStepName.getAttribute("id"),
      apThreeStepName.getAttribute("id"),
    ];
    var count = 0;
    if (apZeroStepName.getAttribute("id") != null) {
      count += 1;
    }
    if (apOneStepName.getAttribute("id") != null) {
      count += 1;
    }
    if (apTwoStepName.getAttribute("id") != null) {
      count += 1;
    }
    if (apThreeStepName.getAttribute("id") != null) {
      count += 1;
    }
    var apLine = new Array();
    for (var i = 0; i < count; i++) {
      apLine[i] = {
        employee: { employeeId: apLineEmpId[i].trim() },
        apStep: i,
      };
    }

    relineEmpId = apReferenceStepName.getAttribute("id");
    aglineEmpId = apAgreementStepName.getAttribute("id");

    if (apLineEmpId[0] != undefined && apLineEmpId[0] != "") {
      if (apDocsTitleObj.value != undefined && apDocsTitleObj.value != "") {
        $.ajax({
          url: backurlApAddDocs + type,
          method: "post",
          contentType: "application/json",
          data: JSON.stringify({
            documentStatus: apDocsTypeObj.trim(),
            documentTitle: apDocsTitleObj.value.trim(),
            documentContent: content,
            employee: { employeeId: loginInfoIdObj.innerText.trim() },
            approvals: apLine,
            agreement: { employee: { employeeId: aglineEmpId.trim() } },
            reference: { employee: { employeeId: relineEmpId.trim() } },
          }),
          success: function () {
            alert("기안이 완료되었습니다.");
            //메뉴 이동 시 변경 될 부분
            var $content = $("div.wrapper>div.main>main.content");
            var href = "approval-board.html";

            $("ul#docMenuFirst a")
              .closest("li")
              .attr("class", "sidebar-item mb-2");
            $("ul#docList").attr("style", "display:block");
            $("ul#docList > li:nth-child(1)").attr(
              "class",
              "sidebar-item mb-2 active"
            );
            $content.load(href, function (responseTxt, statusTxt, xhr) {
              if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
            });
          },
        });
      } else {
        alert("제목이 입력되지 않았습니다");
      }
    } else {
      alert("결재선이 지정되지 않았습니다");
    }
  }

  function addApFormSubmitHandler(e) {
    docFirst();
  }

  $apBtn.click(addApFormSubmitHandler);
}
