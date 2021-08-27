$(function () {
  //문서정보 관련 테이블 구성요소 받아오기
  var tdDetailType = document.getElementById("apDocumentDetailType"); //문서종류 칸
  var tdDetailNo = document.getElementById("apDocumentDetailNo"); //문서번호 칸
  var tdDetailDep = document.getElementById("apDocumentDetailDep"); //기안부서 칸
  var tdDetailWriter = document.getElementById("apDocumentDetailWriter"); //기안자 칸
  var tdDetailDate = document.getElementById("apDocumentDetailDate"); //기안 일시 칸

  //내용 관련 구성요소 받아오기
  var divDetailTitle = document.getElementById("apDocumentDetailTitle"); //문서 제목
  var divDetailContent = document.getElementById("apDocumentContent"); //문서 내용

  //결재 관련 테이블 구성요소 받아오기
  var apStep0Obj = document.getElementById("apApprovalStep0"); //결재 0 칸 , 이미지 or 버튼 들어갈 곳
  var apStep1Obj = document.getElementById("apApprovalStep1"); //결재 1 칸 , 이미지 or 버튼 들어갈 곳
  var apStep2Obj = document.getElementById("apApprovalStep2"); //결재 2 칸 , 이미지 or 버튼 들어갈 곳
  var apStep3Obj = document.getElementById("apApprovalStep3"); //결재 3 칸 , 이미지 or 버튼 들어갈 곳
  var tdApStep0Name = document.getElementById("apApprovalStepName0"); //결재 0 칸 , 이름 들어갈 곳
  var tdApStep1Name = document.getElementById("apApprovalStepName1"); //결재 1 칸 , 이름 들어갈 곳
  var tdApStep2Name = document.getElementById("apApprovalStepName2"); //결재 2 칸 , 이름 들어갈 곳
  var tdApStep3Name = document.getElementById("apApprovalStepName3"); //결재  3 칸 , 이름 들어갈 곳
  var divApStep0Date = document.getElementById("apApprovalStepDate0"); //결재 0 칸 , 날짜 들어갈 곳
  var divApStep1Date = document.getElementById("apApprovalStepDate1"); //결재 1 칸 , 날짜 들아갈 곳
  var divApStep2Date = document.getElementById("apApprovalStepDate2"); //결재 2 칸 , 날짜 들어갈 곳
  var divApStep3Date = document.getElementById("apApprovalStepDate3"); //결재 3 칸 , 날짜 들어갈 곳
  //합의 관련 테이블 구성요소 받아오기
  var tdAgName = document.getElementById("apAgreementName");
  //참조 관련 테이블 구성요소 받아오기
  var tdReName = document.getElementById("apReferenceName");

  //승인요망 버튼 구성요소 생성하기 : 결재선 0,1,2,3 단계 모두 생성
  var buttonTag = document.createElement("button");
  buttonTag.setAttribute("id", "apCommentConfirmBtn");
  buttonTag.setAttribute("class", "btn");
  buttonTag.setAttribute("class", "ap-btn-outline-purple");
  buttonTag.innerHTML = "승인 요망";
  var buttonTag1 = document.createElement("button");
  buttonTag1.setAttribute("id", "apCommentConfirmBtn");
  buttonTag1.setAttribute("class", "btn");
  buttonTag1.setAttribute("class", "ap-btn-outline-purple");
  buttonTag1.innerHTML = "승인 요망";
  var buttonTag2 = document.createElement("button");
  buttonTag2.setAttribute("id", "apCommentConfirmBtn");
  buttonTag2.setAttribute("class", "btn");
  buttonTag2.setAttribute("class", "ap-btn-outline-purple");
  buttonTag2.innerHTML = "승인 요망";
  var buttonTag3 = document.createElement("button");
  buttonTag3.setAttribute("id", "apCommentConfirmBtn");
  buttonTag3.setAttribute("class", "btn");
  buttonTag3.setAttribute("class", "ap-btn-outline-purple");
  buttonTag3.innerHTML = "승인 요망";
  console.log(buttonTag3);
  var agButtonTag = document.createElement("button");
  agButtonTag.setAttribute("id", "approvalCommentBtn");
  agButtonTag.setAttribute("class", "btn");
  agButtonTag.setAttribute("class", "ap-btn-outline-purple");
  agButtonTag.setAttribute("style", "width: 90px");
  agButtonTag.innerHTML = "승인 요망";
  //이미지 구성요소 생성하기 : 결재선 0,1,2,3 단계 모두 생성
  var imgTag = document.createElement("img");
  imgTag.style.width = "100px";
  var imgTag1 = document.createElement("img");
  console.log(imgTag1);
  imgTag1.style.width = "100px";
  var imgTag2 = document.createElement("img");
  imgTag2.style.width = "100px";
  var imgTag3 = document.createElement("img");
  imgTag3.style.width = "100px";
  var agImgTag = document.createElement("img");
  agImgTag.style.width = "60px";
  // 참조 확인용을 위한 구성요소 생성하기
  var spanTag = document.createElement("div");

  //받아올 데이터들
  var tmpDocsBdNo = localStorage.getItem("apDocumentNum"); //선택한 문서번호 : local에 가져오기

  var apDocsType = new Array(); //문서 종류
  var apDocsNo = new Array(); //문서 번호
  var apDocsDep = new Array(); //기안 부서
  var apDocsWriter = new Array(); //기안자
  var apDocsDate = new Array(); //기안 일시

  var apDocsTitle = new Array(); //문서 제목
  var apDocsContent = new Array(); //문서 내용

  var apDocsApName0 = new Array(); //결재자 0 이름
  var apDocsApDate0 = new Array(); //결재자 0 승인날짜
  var apDocsApType0 = new Array(); //결재자 0 승인여부

  var apDocsApName1 = new Array(); //결재자 1 이름
  var apDocsApDate1 = new Array(); //결재자 1 승인날짜
  var apDocsApType1 = new Array(); //결재자 1 승인여부

  var apDocsApName2 = new Array(); //결재자 2 이름
  var apDocsApDate2 = new Array(); //결재자 2 승인날짜
  var apDocsApType2 = new Array(); //결재자 2 승인여부

  var apDocsApName3 = new Array(); //결재자 3 이름
  var apDocsApDate3 = new Array(); //결재자 3 승인날짜
  var apDocsApType3 = new Array(); //결재자 3 승인여부

  var apDocsAgName = new Array(); //합의자 이름
  var apDocsAgType = new Array(); //합의자 승인여부

  var apDocsReName = new Array(); //참조자 이름
  var apDocsReType = new Array(); //참조자 승인여부

  var myCheckName = null; // 내가 승인할 위치인지 확인하기 위해 만든 변수
  var myCheckId = null; //내 로그인 아이디
  //내가 승인해야할 부분 받아오기 ajax
  $.ajax({
    method: "GET",
    transformRequest: [null],
    transformResponse: [null],
    jsonpCallbackParam: "callback",
    url: "http://localhost:8888/gwback/approval/selectmyclick/" + tmpDocsBdNo,
    headers: {
      Accept: "application/json, text/plain, */*",
    },
    data: "",
    timeout: {},
    success: function (responseObj) {
      console.log(responseObj);
      myCheckName = responseObj[0].employee.name;
      myCheckId = responseObj[0].employee.employeeId;
      console.log(myCheckName);
    },
  });

  //상세내용 구성요소 채우기 관련 함수 시작
  function createApBdElement(i) {
    //문서정보 채우기
    tdDetailType.innerHTML = apDocsType;
    tdDetailNo.innerHTML = apDocsNo;
    tdDetailDep.innerHTML = apDocsDep;
    tdDetailWriter.innerHTML = apDocsWriter;
    tdDetailDate.innerHTML = apDocsDate;
    divDetailTitle.innerHTML = apDocsTitle;
    divDetailContent.innerHTML = apDocsContent;

    // 결재 관련
    //결재선 이름 채우기
    tdApStep0Name.innerHTML = apDocsApName0;
    tdApStep1Name.innerHTML = apDocsApName1;
    tdApStep2Name.innerHTML = apDocsApName2;
    tdApStep3Name.innerHTML = apDocsApName3;
    //결재선 승인날짜 채우기
    divApStep0Date.innerHTML = apDocsApDate0;
    divApStep1Date.innerHTML = apDocsApDate1;
    divApStep2Date.innerHTML = apDocsApDate2;
    divApStep3Date.innerHTML = apDocsApDate3;

    tdApStep0Name.appendChild(divApStep0Date);
    tdApStep1Name.appendChild(divApStep1Date);
    tdApStep2Name.appendChild(divApStep2Date);
    tdApStep3Name.appendChild(divApStep3Date);

    //결재 관련 승인여부에 따라, 알맞은 이미지 부여하기
    /*
  //결재단계처리
  const confirmPlsBtn = document.querySelector("#apCommentConfirmBtn"); //승인요망버튼
  //전단계 결재자가 '대기중'일 경우 승인요망 버튼 비활성화
  //0단계는 무조건 승인처리 되므로 1단계 버튼은 비활성화 불필요, 2단계 버튼부터 시작
  alert(apStep1Obj.innerText);
  if (apStep1Obj.innerText == "대기중") {
    confirmPlsBtn.disabled = true;
  } else if (
    apStep1Obj.innerText == "대기중" &&
    apStep2Obj.innerText == "대기중"
  ) {
    confirmPlsBtn.disabled = true;
  }
*/
    var apDocsApTypeArr = [
      apDocsApType0,
      apDocsApType1,
      apDocsApType2,
      apDocsApType3,
    ];
    var apDocsApNameArr = [
      apDocsApName0,
      apDocsApName1,
      apDocsApName2,
      apDocsApName3,
    ];
    var apStepObjArr = [apStep0Obj, apStep1Obj, apStep2Obj, apStep3Obj];
    var imgTagArr = [imgTag, imgTag1, imgTag2, imgTag3];
    /*
  승인, 반려, 대기 
  로그인한사원명과 결재자이름이 같고 이전 결재타입이 모두 승인인 경우만  승인요망버튼이 보여진다 
  */
    var apFlag = true; //결재여부
    for (var i = 0; i < apDocsApTypeArr.length; i++) {
      // console.log(
      //   "---i:" +
      //     i +
      //     ", apDocsApTypeArr[i]:" +
      //     apDocsApTypeArr[i] +
      //     ", apDocsApNameArr[i].toString():" +
      //     apDocsApNameArr[i].toString()
      // );
      if (apDocsApTypeArr[i] == "대기") {
        //
        //이전결재라인이 모두 승인된 경우
        if (myCheckName === apDocsApNameArr[i].toString()) {
          if (!apFlag) {
            //결재 처리가 안 된 경우 버튼 비활성화
            buttonTag.disabled = true;
          }
          //로그인한사원명과 결재자이름이 같으면 버튼 보여주기
          apStepObjArr[i].appendChild(buttonTag);
        } else {
          apStepObjArr[i].innerText = apDocsApTypeArr[i] + "중";
        }
        // }
        apFlag = false;
      } else {
        if (apDocsApTypeArr[i] == "반려") {
          imgTagArr[i].src = "img/icons/no.png";
          apStepObjArr[i].appendChild(imgTag1);
        } else if (apDocsApTypeArr[i] == "승인") {
          imgTagArr[i].src = "img/icons/yes.png";
          apStepObjArr[i].appendChild(imgTagArr[i]);
        }
        apFlag = true;
      }
    }
    // // 결재자 0
    // if (apDocsApType0 == "대기" && myCheckName === apDocsApName0.toString()) {
    //   apStep0Obj.appendChild(buttonTag);
    // } else if (apDocsApType0 == "대기") {
    //   apStep0Obj.innerText = "대기중";
    // } else if (apDocsApType0 == "반려") {
    //   imgTag.src = "img/icons/no.png";
    //   apStep0Obj.appendChild(imgTag);
    // } else if (apDocsApType0 == "승인") {
    //   imgTag.src = "img/icons/yes.png";
    //   apStep0Obj.appendChild(imgTag);
    // }
    // //결재자 1
    // if (apDocsApType1 == "대기" && myCheckName === apDocsApName1.toString()) {
    //   apStep1Obj.appendChild(buttonTag1);
    // } else if (apDocsApType1 == "대기") {
    //   apStep1Obj.innerText = "대기중";
    // } else if (apDocsApType1 == "반려") {
    //   imgTag1.src = "img/icons/no.png";
    //   apStep1Obj.appendChild(imgTag1);
    // } else if (apDocsApType1 == "승인") {
    //   imgTag1.src = "img/icons/yes.png";
    //   apStep1Obj.appendChild(imgTag1);
    // }
    // //결재자 2
    // if (apDocsApType2 == "대기" && myCheckName === apDocsApName2.toString()) {
    //   apStep2Obj.appendChild(buttonTag2);
    // } else if (apDocsApType2 == "대기") {
    //   apStep2Obj.innerHTML = "대기중";
    // } else if (apDocsApType2 == "반려") {
    //   imgTag2.src = "img/icons/no.png";
    //   apStep2Obj.appendChild(imgTag2);
    // } else if (apDocsApType2 == "승인") {
    //   imgTag2.src = "img/icons/yes.png";
    //   apStep2Obj.appendChild(imgTag2);
    // }
    // //결재자 3
    // if (apDocsApType3 == "대기" && myCheckName === apDocsApName3.toString()) {
    //   apStep3Obj.appendChild(buttonTag3);
    // } else if (apDocsApType3 == "대기") {
    //   apStep3Obj.innerText = "대기중";
    // } else if (apDocsApType3 == "반려") {
    //   imgTag3.src = "img/icons/no.png";
    //   apStep3Obj.appendChild(imgTag3);
    // } else if (apDocsApType3 == "승인") {
    //   imgTag3.src = "img/icons/yes.png";
    //   apStep3Obj.appendChild(imgTag3);
    // }

    //합의 이름 채우기
    tdAgName.innerHTML = apDocsAgName;
    console.log(myCheckName === apDocsAgName.toString());
    //합의 관련 승인여부에 따라, 알맞은 이미지 부여하기
    if (apDocsAgType == "대기" && myCheckName === apDocsAgName.toString()) {
      tdAgName.appendChild(agButtonTag);
    } else if (apDocsAgType == "반려") {
      agImgTag.src = "img/icons/no.png";
      tdAgName.appendChild(agImgTag);
    } else if (apDocsAgType == "승인") {
      agImgTag.src = "img/icons/yes.png";
      tdAgName.appendChild(agImgTag);
    }

    //참조 이름 채우기
    tdReName.innerHTML = apDocsReName;
    //참조 관련 승인여부에 따라, 알맞은 이미지 부여하기
    if (apDocsReType == "대기") {
      spanTag.style.color = "#dfd5f5";
      spanTag.setAttribute("class", "fa fa-question");
      tdReName.appendChild(spanTag);
    } else if (apDocsReType == "승인") {
      spanTag.style.color = "#6A0888";
      spanTag.setAttribute("class", "fa fa-check");
      tdReName.appendChild(spanTag);
    }
  }
  //상세내용 구성요소 채우기 관련 함수 끝

  //참조 버튼 클릭시 실행하는 것 관련
  //참조 승인관련 hover 효과 주기
  $("#apReferenceName").hover(
    function () {
      $("#apReferenceName>div.fa-question").css("color", "#6A0888");
    },
    function () {
      $("#apReferenceName>div.fa-question").css("color", "#dfd5f5");
    }
  );
  //로그인 아이디 받아오기
  var loginInfoIdObj = document.querySelector(
    "div.profileDropdown span.loginId"
  );
  var currentLoginId = loginInfoIdObj.innerText;
  //참조 버튼 클릭시 알림창 + ajax 처리
  $("#apReferenceName").click(function () {
    console.log(myCheckName);
    console.log(apDocsReName.toString());
    if (apDocsReType == "대기" && myCheckName === apDocsReName.toString()) {
      console.log("조건 클릭");
      //ajax 관련 내용 넣기
      $.ajax({
        method: "PUT",
        transformRequest: [null],
        transformResponse: [null],
        jsonpCallbackParam: "callback",
        url: "http://localhost:8888/gwback/approval/updatere/" + tmpDocsBdNo,
        headers: {
          Accept: "application/json, text/plain, */*",
          "Content-Type": "application/json;charset=utf-8",
        },
        data: JSON.stringify({
          documentNo: tmpDocsBdNo,
          employee: { employeeId: myCheckId },
        }),
        timeout: {},
        success: function () {
          alert("참조 확인하셨습니다 !");
          spanTag.style.color = "#6A0888";
          spanTag.setAttribute("class", "fa fa-check");
          var $content = $("div.wrapper>div.main>main.content");
          var href = "approval-detail.html";
          $content.load(href, function (responseTxt, statusTxt, xhr) {
            if (statusTxt == "error")
              alert("Error: " + xhr.status + ": " + xhr.statusText);
          });
        },
        error: function (request, status, error) {
          alert(
            "code:" +
              request.status +
              "\n" +
              "message:" +
              request.responseText +
              "\n" +
              "error:" +
              error
          );
        },
      });
    }
  });

  //문서 상세정보 데이터 가지고오는 ajax
  $.ajax({
    method: "GET",
    transformRequest: [null],
    transformResponse: [null],
    jsonpCallbackParam: "callback",
    url: "/gwback/approval/docsdetail/" + tmpDocsBdNo,
    headers: {
      Accept: "application/json, text/plain, */*",
    },
    data: "",
    timeout: {},
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        //문서 정보 받아오기
        apDocsTitle[i] = e.documentTitle;
        apDocsType[i] = e.documentStatus.documentType;
        apDocsNo[i] = e.documentNo;
        apDocsDep[i] = e.employee.department.departmentTitle;
        apDocsWriter[i] = e.employee.employeeId;
        apDocsWriter[i] = e.employee.name;
        apDocsDate[i] = e.draftDate;
        apDocsContent[i] = e.documentContent;

        apDocsApName0[i] = e.approvals[0].employee.name;
        apDocsApType0[i] = "승인"; // 결재자 0번은 무조건 승인처리하므로, 고정값 입력
        apDocsApDate0[i] = e.approvals[0]?.apDate;
        apDocsApName1[i] = e.approvals[1]?.employee?.name;
        apDocsApType1[i] = e.approvals[1]?.apStatus?.apType;
        apDocsApDate1[i] = e.approvals[1]?.apDate;
        apDocsApName2[i] = e.approvals[2]?.employee?.name;
        apDocsApType2[i] = e.approvals[2]?.apStatus?.apType;
        apDocsApDate2[i] = e.approvals[2]?.apDate;
        apDocsApName3[i] = e.approvals[3]?.employee?.name;
        apDocsApType3[i] = e.approvals[3]?.apStatus?.apType;
        apDocsApDate3[i] = e.approvals[3]?.apDate;

        apDocsAgName[i] = e.agreement?.employee?.name;
        apDocsAgType[i] = e.agreement?.agStatus?.apType;
        apDocsReName[i] = e.reference?.employee?.name;
        apDocsReType[i] = e.reference?.reStatus?.apType;
      });

      for (var i = 0; i < apDocsType.length; i++) {
        console.log(apDocsType[i]);
      }

      for (var i = 0; i < apDocsTitle.length; i++) {
        createApBdElement(i);
      }
    },
  });

  //코멘트 관련
  var commentId = new Array(); //코멘트 id
  var commentDate = new Array(); //코멘트 날짜
  var commentCmt = new Array(); //코멘트 내용

  var commentObj = document.getElementById("apCommentTbody"); //코멘트 내용넣을 tbody객체 받아오기

  // 코멘트 구성요소 만들기 + 값 채워넣기
  function createCommentElement(i) {
    var tr = document.createElement("tr");
    var td1 = document.createElement("td"); //코멘트 id들어갈곳
    var td2 = document.createElement("td"); //코멘트 날짜 들어갈 곳
    var td3 = document.createElement("td"); //코멘트 내용 들어갈 곳

    td1.innerHTML = commentId[i];
    td2.innerHTML = commentCmt[i];
    td3.innerHTML = commentDate[i];

    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);

    commentObj.appendChild(tr);
  }

  //코멘트 데이터값 받아오기 ajax
  $.ajax({
    method: "GET",
    transformRequest: [null],
    transformResponse: [null],
    jsonpCallbackParam: "callback",
    url: "/gwback/approval/selectcomments/" + tmpDocsBdNo,
    headers: {
      Accept: "application/json, text/plain, */*",
    },
    data: "",
    timeout: {},
    success: function (responseData) {
      console.log(commentObj);
      console.log(responseData);
      $(responseData).each(function (i, e) {
        commentId[i] = e.employee.employeeId;
        console.log(commentId);
        commentDate[i] = e.apDate;
        console.log(commentDate);
        commentCmt[i] = e.apComment;
      });
      console.log(commentId.length);
      for (var i = 0; i < commentId.length; i++) {
        createCommentElement(i);
      }
    },
  });

  // 모달 관련
  var modal = document.getElementById("modalApprovalComment"); //모달 전체 객체
  var confirmBtn = document.querySelector("#apStatusConfirmBtn"); //저장버튼
  console.log(confirmBtn);
  var cancelBtn = document.getElementById("apCommentCancelBtn"); //취소버튼

  const openModal = (e) => {
    console.log(e.target.id);
    if (e.target.id == "apCommentConfirmBtn") {
      //결재 버튼 클릭시 작동
      apForm();
    } else if (e.target.id == "approvalCommentBtn") {
      //합의 버튼 클릭시 작동
      agForm();
    }
    modal.classList.remove("hidden");
  };

  const cancelModal = () => {
    modal.classList.add("hidden");
  };

  buttonTag.addEventListener("click", openModal);
  buttonTag1.addEventListener("click", openModal);
  buttonTag2.addEventListener("click", openModal);
  buttonTag3.addEventListener("click", openModal);
  agButtonTag.addEventListener("click", openModal); //버튼을 띄워줄려면, 만든건 직접적으로 클릭 이벤트

  //승인 모달 관련 값 체크함수
  function audmitBtnHandler(e) {
    console.log(e.target);
    console.log(e.target.id);
    if (e.target.id == "apCommentOkImg") {
      //승인 선택시
      audmitStatus = "audmit";
      myStatus = "승인";
      console.log("승인 클릭");
    } else if (e.target.id == "apCommentNoImg") {
      //반려 선택시
      audmitStatus = "refuse";
      myStatus = "반려";
      console.log("반려 클릭");
    }
  }
  //코멘트 관련 클릭 발동
  var okBtn = $("#apCommentOkImg");
  var noBtn = $("#apCommentNoImg");
  console.log(okBtn);

  console.log(noBtn);
  okBtn.click(audmitBtnHandler);
  noBtn.click(audmitBtnHandler);

  //문장 받아오기
  var commentTextArea = $("#apCommentInput");
  var lines = commentTextArea.value.split("\n");

  //문단과, 태그 처리
  var resultString = "<p>";
  for (var i = 0; i < lines.length; i++) {
    resultString += lines[i] + "<br />";
  }
  resultString += "</p>";
  //결재자의 승인요망을 클릭하면 작동하는 함수
  function apForm() {
    console.log(resultString);
    confirmBtn.addEventListener("click", function (e) {
      if (audmitStatus == "" && myStatus == "") {
        //승인 타입 선택 안하면 작동
        alert("승인 타입을 선택하세요.");
      } else {
        $.ajax({
          method: "PUT",
          transformRequest: [null],
          transformResponse: [null],
          jsonpCallbackParam: "callback",
          url: "/gwback/approval/updateap/" + audmitStatus,
          headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json;charset=utf-8",
          },
          data: JSON.stringify({
            documentNo: tmpDocsBdNo,
            apComment: resultString,
            employee: { employeeId: myCheckId },
            agStatus: { apType: myStatus },
          }),
          timeout: {},
          success: function (responseData) {
            alert("승인 요망이 확인되었습니다");
            var $content = $("div.wrapper>div.main>main.content");
            var href = "approval-detail.html";
            $content.load(href, function (responseTxt, statusTxt, xhr) {
              if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
            });
          },
          error: function (request, status, error) {
            alert(
              "code:" +
                request.status +
                "\n" +
                "message:" +
                request.responseText +
                "\n" +
                "error:" +
                error
            );
          },
        });
      }
    });
  }
  //합의자의 승인요망을 클릭하면 작동하는 함수
  function agForm() {
    confirmBtn.addEventListener("click", function (e) {
      //코멘트 보내는
      if (audmitStatus == "" && myStatus == "") {
        alert("승인 타입을 선택하세요.");
      } else {
        $.ajax({
          method: "PUT",
          transformRequest: [null],
          transformResponse: [null],
          jsonpCallbackParam: "callback",
          url: "/gwback/approval/updateag/" + audmitStatus,
          headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json;charset=utf-8",
          },
          data: JSON.stringify({
            documentNo: tmpDocsBdNo,
            apComment: resultString,
            employee: { employeeId: myCheckId },
            agStatus: { apType: myStatus },
          }),
          timeout: {},
          success: function (responseData) {
            alert("승인 요망이 확인되었습니다");
            var $content = $("div.wrapper>div.main>main.content");
            var href = "approval-detail.html";
            $content.load(href, function (responseTxt, statusTxt, xhr) {
              if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
            });
          },
          error: function (request, status, error) {
            alert(
              "code:" +
                request.status +
                "\n" +
                "message:" +
                request.responseText +
                "\n" +
                "error:" +
                error
            );
          },
        });
      }
    });
  }
  cancelBtn.addEventListener("click", cancelModal);
});
