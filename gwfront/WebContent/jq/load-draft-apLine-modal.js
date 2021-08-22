localStorage.removeItem("apLineName");

//모달창 객체 가져옴
var modalApLineSettingObj = document.querySelector("div#modalApprovalSetting");
//모달창 내 결재 버튼 객체 가져옴
var modalApBtnObj = modalApLineSettingObj.querySelector(
  "button#modalBtnApproval"
);
//모달창 내 합의 버튼 객체 가져옴
var modalAgBtnObj = modalApLineSettingObj.querySelector(
  "button#modalBtnAgreement"
);
//모달창 내 참조 버튼 객체 가져옴
var modalReBtnObj = modalApLineSettingObj.querySelector(
  "button#modalBtnReference"
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

//현재 로그인한 사용자의 이름
var loginInfoNameObj = document.querySelector(
  "div.profileDropdown span.loginName"
);
var loginInfoIdObj = document.querySelector("div.profileDropdown span.loginId");
console.log(loginInfoIdObj);
//모달창 내 사원 관련 table td 객체 모두 가져옴
var apStaffName = document.querySelectorAll("div#apLineCardbody table td");
// console.log(apStaffName);

//모달창 내 form 객체 가져옴
// var apSetApLineFormObj = document.querySelector("form.apLineSelectForm");

var apSetApLineSaveBtnObj = document.querySelector("button#apSettingSave");
//staffName 클릭 시 클릭 핸들러, localStorage의 값을 타겟의 innerText값(이름값)으로 설정
function apStaffNameClickHandler(e) {
  // console.log(e.target.id);
  localStorage.setItem("apLineName", e.target.innerText);
  localStorage.setItem("apLineId", e.target.id);
}

//이벤트 적용
for (var i = 0; i < apStaffName.length; i++) {
  apStaffName[i].addEventListener("click", apStaffNameClickHandler);
}

//각 사원의 이름을 가지고 있는 td객체를 배열로 선언해줌
var modalApStepNameArr = [
  modalApZeroNameObj,
  modalApFirstNameObj,
  modalApSecondNameObj,
  modalApThirdNameObj,
];

//현재 결재 단계를 지정해주는 변수
var apStep = 1;

//로그인한 사원을 작성자로 보고 0번째 결재자를 자신의 이름으로 설정
modalApStepNameArr[0].innerHTML = loginInfoNameObj.innerText;
modalApStepNameArr[0].setAttribute("id", loginInfoIdObj.innerText);
localStorage.setItem("apLineName", loginInfoNameObj.innerText);
localStorage.setItem("apLineId", loginInfoIdObj.innerText);
//결재 버튼 클릭 시 클릭핸들러
//apLineName의 값이 undefined일때는 추가 x, apStep이 4여도 추가 x
//apStep이 4미만이면서 사용자가 선택한 사원이 있는 경우에만 추가하고 apStep 증가
function modalApBtnClickHandler() {
  var apChkBool = true;
  if (localStorage.getItem("apLineName") == undefined) {
    alert("선택한 사원이 없습니다");
  } else {
    if (apStep == 4) {
      alert("더이상 추가할 수 없습니다");
    } else {
      //결재, 합의 ,참조 테이블에 같은 사번의 사원이 추가되었는지 확인
      for (var i = 0; i < apStep; i++) {
        if (
          modalApStepNameArr[i].getAttribute("id") ==
          localStorage.getItem("apLineId")
        ) {
          apChkBool = false;
        }
      }
      if (
        modalAgNameObj.getAttribute("id") == localStorage.getItem("apLineId")
      ) {
        apChkBool = false;
      }
      if (
        modalReNameObj.getAttribute("id") == localStorage.getItem("apLineId")
      ) {
        apChkBool = false;
      }
      //같은 사번의 사원이 없을 때 추가
      if (apChkBool) {
        modalApStepNameArr[apStep].innerHTML =
          localStorage.getItem("apLineName");
        modalApStepNameArr[apStep].setAttribute(
          "id",
          localStorage.getItem("apLineId")
        );
        apStep += 1;
      } else {
        alert("이미 선택된 사원입니다");
      }
    }
  }
}

//합의 버튼 클릭 시 클릭 핸들러
//localStorage apLineName의 값이 undefined일때 추가 x
//이미 해당 버튼 내에 이름이 있는 경우에 추가 x
function modalAgBtnClickHandler() {
  var agChkBool = true;
  if (localStorage.getItem("apLineName") == undefined) {
    alert("선택한 사원이 없습니다");
  } else {
    //결재, 합의 ,참조 테이블에 같은 사번의 사원이 추가되었는지 확인
    for (var i = 0; i < apStep; i++) {
      if (
        modalApStepNameArr[i].getAttribute("id") ==
        localStorage.getItem("apLineId")
      ) {
        agChkBool = false;
      }
    }
    if (modalAgNameObj.getAttribute("id") == localStorage.getItem("apLineId")) {
      agChkBool = false;
    }
    if (modalReNameObj.getAttribute("id") == localStorage.getItem("apLineId")) {
      agChkBool = false;
    }
    //같은 사번의 사원이 없을 때 추가
    if (agChkBool) {
      if (modalAgNameObj.innerText == "") {
        modalAgNameObj.innerText = localStorage.getItem("apLineName");
        modalAgNameObj.setAttribute("id", localStorage.getItem("apLineId"));
      } else {
        alert("이미 선택한 사원이 있습니다");
      }
    } else {
      alert("이미 선택된 사원입니다");
    }
  }
}

//참조 버튼 클릭 시 클릭 핸들러
//localStorage apLineName의 값이 undefined일때 추가 x
//이미 해당 버튼 내에 이름이 있는 경우에 추가 x
function modalReBtnClickHandler() {
  var reChkBool = true;
  if (localStorage.getItem("apLineName") == undefined) {
    alert("선택한 사원이 없습니다");
  } else {
    //결재, 합의 ,참조 테이블에 같은 사번의 사원이 추가되었는지 확인
    for (var i = 0; i < apStep; i++) {
      if (
        modalApStepNameArr[i].getAttribute("id") ==
        localStorage.getItem("apLineId")
      ) {
        reChkBool = false;
      }
    }
    if (modalAgNameObj.getAttribute("id") == localStorage.getItem("apLineId")) {
      reChkBool = false;
    }
    if (modalReNameObj.getAttribute("id") == localStorage.getItem("apLineId")) {
      reChkBool = false;
    }
    //같은 사번의 사원이 없을 때 추가
    if (reChkBool) {
      if (modalReNameObj.innerText == "") {
        modalReNameObj.innerText = localStorage.getItem("apLineName");
        modalReNameObj.setAttribute("id", localStorage.getItem("apLineId"));
      } else {
        alert("이미 선택한 사원이 있습니다");
      }
    } else {
      alert("이미 선택된 사원입니다");
    }
  }
}

//합의자 이름이 있는 버튼 클릭 시 클릭 핸들러
//innerText를 초기화해줌
function modalAgNameBtnClickHandler() {
  console.log(modalAgNameObj);
  modalAgNameObj.removeAttribute("id");
  modalAgNameObj.innerText = "";
}

//참조자 이름이 있는 버튼 클릭 시 클릭 핸들러
//innerText를 초기화해줌
function modalReNameBtnClickHandler() {
  modalReNameObj.innerText = "";
  modalReNameObj.removeAttribute("id");
}

//합의, 참조자 이름 있는 버튼 클릭 핸들러 addEvent
modalAgNameObj.addEventListener("click", modalAgNameBtnClickHandler);
modalReNameObj.addEventListener("click", modalReNameBtnClickHandler);

//결재, 합의 ,참조 버튼 클릭 핸듫러 addEvent
modalApBtnObj.addEventListener("click", modalApBtnClickHandler);
modalAgBtnObj.addEventListener("click", modalAgBtnClickHandler);
modalReBtnObj.addEventListener("click", modalReBtnClickHandler);
