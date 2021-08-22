//로그인정보
var loginInfoIdObj = document.querySelector("div.profileDropdown span.loginId");

var skdTBodyObj = document.querySelector("tbody.skdTbody");
//상세내역 모달에 데이터
var shareObj = document.getElementById("skdDetailShare");
//console.log(shareObj);
var shareValue = shareObj.querySelector("td.skdDetailInputData");
//console.log(shareValue);
var typeObj = document.getElementById("skdDetailType");
var typeValue = typeObj.querySelector("td.skdDetailInputData");
//console.log(typeValue);
var titleObj = document.getElementById("skdDetailTitle");
var titleValue = titleObj.querySelector("td.skdDetailInputData");

var StartTimeObj = document.getElementById("skdDetailStartTime");
var StartTimeValue = StartTimeObj.querySelector("td.skdDetailInputData");

//console.log(StartTimeValue);
var EndTimeObj = document.getElementById("skdDetailEndTime");
var EndTimeValue = EndTimeObj.querySelector("td.skdDetailInputData");

//console.log(EndTimeValue);
var ContentObj = document.getElementById("skdDetailContent");
var ContentValue = ContentObj.querySelector("td.skdDetailInputData");

var type = typeValue.innerHTML;
//typeValue.innerHTML = "회의";
var title = titleValue.innerText;
var start = StartTimeValue.innerText;
var end = EndTimeValue.innerText;
var content = ContentValue.innerText;

var skdDate = [];
var skdTime = [];
var skdContent = [];

//표만들기
function createSkdElement(i) {
  var tr = document.createElement("tr");
  var tdDate = document.createElement("td");
  tdDate.innerHTML = skdDate[i];
  var tdTime = document.createElement("td");
  tdTime.innerHTML = skdTime[i];
  var tdContent = document.createElement("td");
  //내용클릭시 모달띄우기
  var a = document.createElement("a");
  a.setAttribute("href", "#");
  a.addEventListener("click", function (e) {
    localStorage.setItem("searchSkdNoDetail", e.target.id);
    createModal("skdDetail");
  });
  a.setAttribute("id", i);
  a.innerText = skdContent[i];
  //자식에 부모달아주기
  tdContent.appendChild(a);
  tr.appendChild(tdDate);
  tr.appendChild(tdTime);
  tr.appendChild(tdContent);
  skdTBodyObj.appendChild(tr);
}

//일정개수만큼 표를 생성하여 데이터 넣는 함수
function init(result) {
  console.log({ result });

  result.forEach((item) => {
    start = new Date(item.skdStartDate);
    end = new Date(item.skdEndDate);
    title = item.skdTitle;

    skdContent.push(title);
    skdDate.push(moment(start).format("YY-MM-DD"));
    //9:00 AM ~ 10:00 AM의 형태로 보여주는 moment함수
    skdTime.push(moment(start).format("LT") + " ~ " + moment(end).format("LT"));
  });
  for (var i = 0; i < skdDate.length; i++) {
    createSkdElement(i);
  }
}

function createModalValue(result) {
  console.log({ result });
  let jsonStr = JSON.stringify({ result });
  console.log(jsonStr);
  console.log(type);
  type = result.skdType;
  title = result.skdTitle;
  console.log(title);
  start = result.skdStartDate;
  end = result.skdStartDate;
  content = result.skdContent;
}

var skdTitleDetailSearch;
var skdContentDetailSearch;
//modal 만드는 함수
//파라미터 : class="modal" 의 id
var skdno;
function createModal(id) {
  skdno = localStorage.getItem("skdNo");
  console.log(skdno + "현재skdno");
  var index = localStorage.getItem("searchSkdNoDetail");

  var loginedId = localStorage.getItem("loginInfo");
  var loginedDept = loginedId.substring(0, 3);
  var sid = loginedId;
  var dept = loginedDept;
  //주소창의 파라미터를 다른값으로 바꾸기 위해서 URLSearchParams 사용
  const urlSearchParams = new URLSearchParams(window.location.search);
  //Object.entries() ==> 가지고 있는 값을 key와 value의 배열형태로 반환
  const urlParams = Object.fromEntries(urlSearchParams.entries());
  //urlParams.id = sid;
  //urlParams.dept_id = dept;
  console.log(urlParams);
  // console.log(skdTitleDetailSearch);
  var modal = document.getElementById(id);
  modal.classList.remove("hidden"); //모달열기
  var closeBtn = modal.querySelector("button.cancel");
  var overlay = modal.querySelector(".modal_overlay");
  var deleteBtn = modal.querySelector("button.deleteBtn");
  var xBoxBtn = modal.querySelector("button.xBox");
  var modifyBtn = modal.querySelector("button.modifyBtn");
  var modifySubmitBtnObj = modal.querySelector("button.modifySubmit");

  //함수
  var openModal = () => {
    modal.classList.remove("hidden");
  };

  var closeModal = () => {
    modal.classList.add("hidden");
  };

  //클릭이벤트
  //오버레이 부분 클릭 닫기
  overlay.addEventListener("click", closeModal);
  //모달창 닫기 버튼
  if (xBoxBtn != null) {
    xBoxBtn.addEventListener("click", closeModal);
  }

  if (closeBtn != null) {
    closeBtn.addEventListener("click", closeModal);
  }
  if (modifyBtn != null) {
    modifyBtn.addEventListener("click", function () {
      modal.classList.add("hidden");

      createModal("skdModifyDetail");
    });
  }
  $.ajax({
    url: "/gwback/schedule/skdDetail/" + skdno,
    method: "GET",
    transformRequest: [null],
    transformResponse: [null],
    jsonpCallbackParam: "callback",
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        console.log("선택 결과값" + e);
        titleValue.innerHTML = e.skdTitle;
        typeValue.innerHTML = e.skdType.skdType;
        StartTimeValue.innerHTML = e.skdStartDate;
        EndTimeValue.innerHTML = e.skdEndDate;
        ContentValue.innerHTML = e.skdContent;

        localStorage.setItem("skdNo", e.skdNo);
        localStorage.setItem("title", titleValue.innerHTML);
        localStorage.setItem(
          "startDate",
          StartTimeValue.innerHTML.slice(0, 10)
        );
        localStorage.setItem(
          "startTime",
          StartTimeValue.innerHTML.slice(11, 16)
        );
        localStorage.setItem("endDate", EndTimeValue.innerHTML.slice(0, 10));
        localStorage.setItem("endTime", EndTimeValue.innerHTML.slice(11, 16));
        localStorage.setItem("content", ContentValue.innerHTML);
      });
    },
  });
  $.ajax({
    url:
      "http://localhost:8888/gwback/schedule/skdContent/" +
      urlParams.skd_title +
      "/" +
      urlParams.skd_content,
    method: "GET",
    transformRequest: [null],
    transformResponse: [null],
    jsonpCallbackParam: "callback",
    // data: urlParams,
    success: function (responseData) {
      console.log(responseData);
      $(responseData).each(function (i, e) {
        if (index == i) {
          // console.log(e.skd_title);
          console.log(e);
          titleValue.innerHTML = e.skdTitle;
          console.log(e.skdTitle);
          typeValue.innerHTML = e.skdType.skdType;
          StartTimeValue.innerHTML = e.skdStartDate;
          EndTimeValue.innerHTML = e.skdEndDate;
          ContentValue.innerHTML = e.skContent;
          console.log(e.skdContent);

          localStorage.setItem("skdNo", e.skdNo);
          localStorage.setItem("title", titleValue.innerHTML);
          localStorage.setItem(
            "startDate",
            StartTimeValue.innerHTML.slice(0, 10)
          );
          localStorage.setItem(
            "startTime",
            StartTimeValue.innerHTML.slice(11, 16)
          );
          localStorage.setItem("endDate", EndTimeValue.innerHTML.slice(0, 10));
          localStorage.setItem("endTime", EndTimeValue.innerHTML.slice(11, 16));
          localStorage.setItem("content", ContentValue.innerHTML);
        }
      });
    },
  });

  $.ajax({
    url:
      "http://localhost:8888/gwback/schedule/skdDate/" +
      urlParams.skd_start_date +
      "/" +
      urlParams.skd_end_date,
    method: "GET",
    transformRequest: [null],
    transformResponse: [null],
    jsonpCallbackParam: "callback",
    // data: urlParams,
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        if (index == i) {
          console.log(e);
          titleValue.innerHTML = e.skdTitle;
          typeValue.innerHTML = e.skdType.skdType;
          StartTimeValue.innerHTML = e.skdStartDate;
          EndTimeValue.innerHTML = e.skdEndDate;
          ContentValue.innerHTML = e.skdContent;
          localStorage.setItem("skdNo", e.skdNo);
          localStorage.setItem("title", titleValue.innerHTML);
          localStorage.setItem(
            "startDate",
            StartTimeValue.innerHTML.slice(0, 10)
          );
          localStorage.setItem(
            "startTime",
            StartTimeValue.innerHTML.slice(11, 16)
          );
          localStorage.setItem("endDate", EndTimeValue.innerHTML.slice(0, 10));
          localStorage.setItem("endTime", EndTimeValue.innerHTML.slice(11, 16));
          localStorage.setItem("content", ContentValue.innerHTML);
        }
      });
    },
  });
}

//내용,제목 검색
document.addEventListener("DOMContentLoaded", function () {
  // var id = loginInfoIdObj.innerHTML;

  var loginedId = localStorage.getItem("loginInfo");
  var id = loginedId;
  //주소창의 파라미터를 다른값으로 바꾸기 위해서 URLSearchParams 사용
  const urlSearchParams = new URLSearchParams(window.location.search);
  //Object.entries() ==> 가지고 있는 값을 key와 value의 배열형태로 반환
  const urlParams = Object.fromEntries(urlSearchParams.entries());
  //urlParams.id = id;

  console.log({ urlParams });
  console.log("dddd" + urlParams.skd_content + urlParams.skd_title);
  $.ajax({
    url:
      "http://localhost:8888/gwback/schedule/skdContent/" +
      urlParams.skd_title +
      "/" +
      urlParams.skd_content,
    method: "GET",
    transformRequest: [null],
    transformResponse: [null],
    jsonpCallbackParam: "callback",
    success: function (responseData) {
      console.log(responseData + "init함수 내용");
      //위에 init함수 호출
      console.log(
        "제목내용검색responseData--" +
          responseData.skdTitle +
          "내용:" +
          responseData.skdContent +
          "타임:"
      );
      init(responseData);
    },
  });
});

//기간검색
document.addEventListener("DOMContentLoaded", function () {
  var loginedId = localStorage.getItem("loginInfo");
  var loginedDept = loginedId.substring(0, 3);
  var id = loginedId;
  var dept = loginedDept;
  //주소창의 파라미터를 다른값으로 바꾸기 위해서 URLSearchParams 사용
  const urlSearchParams = new URLSearchParams(window.location.search);
  //Object.entries() ==> 가지고 있는 값을 key와 value의 배열형태로 반환
  const urlParams = Object.fromEntries(urlSearchParams.entries());
  //urlParams.id = id;
  //urlParams.dept_id = dept;

  console.log({ urlParams });
  $.ajax({
    url:
      "http://localhost:8888/gwback/schedule/skdDate/" +
      urlParams.start_date +
      "/" +
      urlParams.end_date,
    method: "GET",
    transformRequest: [null],
    transformResponse: [null],
    jsonpCallbackParam: "callback",
    success: function (responseData) {
      //위에 init함수 호출
      init(responseData);
    },
  });
});
