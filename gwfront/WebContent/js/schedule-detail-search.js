let teamOrPersonalOption;
let skdinfo = {};
//로그인정보
var loginInfoIdObj = document.querySelector("div.profileDropdown span.loginId");

var skdTBodyObj = document.querySelector("tbody.skdTbody");
var shareObj = document.getElementById("skdDetailShare");
var shareValue = shareObj.querySelector("td.skdDetailInputData");
var typeObj = document.getElementById("skdDetailType");
var typeValue = typeObj.querySelector("td.skdDetailInputData");
var titleObj = document.getElementById("skdDetailTitle");
var titleValue = titleObj.querySelector("td.skdDetailInputData");
console.log(titleValue);

var StartTimeObj = document.getElementById("skdDetailStartTime");
var StartTimeValue = StartTimeObj.querySelector("td.skdDetailInputData");

//console.log(StartTimeValue);
var EndTimeObj = document.getElementById("skdDetailEndTime");
var EndTimeValue = EndTimeObj.querySelector("td.skdDetailInputData");

//console.log(EndTimeValue);
var ContentObj = document.getElementById("skdDetailContent");
var ContentValue = ContentObj.querySelector("td.skdDetailInputData");
console.log(ContentValue);
var type = typeValue.innerHTML;
var title = titleValue.innerText;
var start = StartTimeValue.innerText;
var end = EndTimeValue.innerText;
var content = ContentValue.innerText;

var skdDate = [];
var skdTime = [];
var skdContent = [];
var skdNoArr = [];

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
    console.log(e.target.id);
    localStorage.setItem("skdNo", e.target.classList[0]);
    createModal("skdDetail", e.target.classList[0]); //omj
  });
  a.setAttribute("id", i);
  a.setAttribute("class", skdNoArr[i]);
  a.innerText = skdContent[i];
  //자식에 부모달아주기
  tdContent.appendChild(a);
  tr.appendChild(tdDate);
  tr.appendChild(tdTime);
  tr.appendChild(tdContent);
  skdTBodyObj.appendChild(tr);
}

//일정개수만큼 표를 생성하여 데이터 넣는 함수
function init(result, check) {
  console.log({ result });
  console.log(check);
  result.forEach((item) => {
    start = new Date(item.skdStartDate);
    end = new Date(item.skdEndDate);
    title = item.skdTitle;

    skdContent.push(title);
    skdDate.push(moment(start).format("YY-MM-DD"));
    //9:00 AM ~ 10:00 AM의 형태로 보여주는 moment함수
    skdTime.push(moment(start).format("LT") + " ~ " + moment(end).format("LT"));
    skdNoArr.push(item.skdNo);
    console.log(skdNoArr);
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
//var skdno;
function createModal(id, skdno) {
  // skdno = localStorage.getItem("skdNo");
  $("div.skdNo").html(skdno);
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
      var skdno = $("div.skdNo").html(); //omj
      createModal("skdModifyDetail", skdno); //omj
    });
  }
  $.ajax({
    url: "/gwback/schedule/skdDetail/" + skdno,
    async: false,
    method: "GET",
    transformRequest: [null],
    transformResponse: [null],
    jsonpCallbackParam: "callback",
    success: function (e) {
      titleValue.innerHTML = e.skdTitle;
      typeValue.innerHTML = e.skdType.skdType;
      StartTimeValue.innerHTML = e.skdStartDate;
      EndTimeValue.innerHTML = e.skdEndDate;
      ContentValue.innerHTML = e.skdContent;
      //localStorage.setItem("skdNo", e.skdNo);

      //---omj
      skdinfo.title = titleValue.innerHTML;
      skdinfo.startDate = StartTimeValue.innerHTML.slice(0, 10);
      skdinfo.startTime = StartTimeValue.innerHTML.slice(11, 16);
      skdinfo.endDate = EndTimeValue.innerHTML.slice(0, 10);
      skdinfo.endTime = EndTimeValue.innerHTML.slice(11, 16);
      skdinfo.content = ContentValue.innerHTML;

      // localStorage.setItem("title", titleValue.innerHTML);
      // localStorage.setItem("startDate", StartTimeValue.innerHTML.slice(0, 10));
      // localStorage.setItem("startTime", StartTimeValue.innerHTML.slice(11, 16));
      // localStorage.setItem("endDate", EndTimeValue.innerHTML.slice(0, 10));
      // localStorage.setItem("endTime", EndTimeValue.innerHTML.slice(11, 16));
      // localStorage.setItem("content", ContentValue.innerHTML);
      // localStorage.setItem("share", e.skdShare);
      teamOrPersonalOption = e.skdShare;
      console.log(skdinfo);
    },
  });
  console.log(skdinfo);

  $("[class*='deleteBtn']").click(function () {
    var backurlDeleteSkd = "/gwback/schedule/remove/" + skdno;
    $.ajax({
      url: backurlDeleteSkd,
      method: "DELETE",
      transformRequest: [null],
      transformResponse: [null],
      jsonpCallbackParam: "callback",
      headers: {
        Accept: "application/json, text/plain, */*",
      },
      success: function () {
        alert("일정이 삭제되었습니다!");
        location.reload();
        // loadSchedule();
      },
    });
    // e.preventDefault();
  });

  var backSkdModify = "/gwback/schedule/modify/" + skdno;

  //var modifySkdFormObj = $("#modifySkdContent");
  var modifySkdSubmitBtn = $("button.modifySkdSubmit");
  var skdUpdateTypeObj = $("#skdUpdateTypeSelect"); //problem

  var skdUpdateTypeValue = "업무";
  skdUpdateTypeObj.change(function () {
    console.log(this.value);
    skdUpdateTypeValue = this.value + "";
  });

  var skdUpdateTitle = $("#update_title");
  var skdUpdateContent = $("#input_content_update"); //내용
  var skdUpdateStartDate = $("#start_date_update"); //
  var skdUpdateStartTime = $("#start_time_update"); //
  var skdUpdateEndDate = $("#end_date_update"); //종료날짜
  var skdUpdateEndTime = $("#end_time_update"); //종료시간
  var skdUpdateShare2 = $('input[name="radio-2"]'); //problem
  var currentSkdNo = localStorage.getItem("skdNo");

  var test = "p";
  skdUpdateShare2.change(function () {
    console.log(this.value);
    test = this.value;
    console.log(test);
  });

  $("#skdModifyBtn").click(function () {
    var UpdatePreTitleValue = skdinfo.title;

    //localStorage.getItem("title");
    var UpdatePreStartDate = skdinfo.startDate;
    // localStorage.getItem("startDate");
    var UpdatePreStartTime = skdinfo.startTime;
    //localStorage.getItem("startTime");
    var skdOriginEndDate = skdinfo.endDate;
    //localStorage.getItem("endDate");
    var skdOriginEndTime = skdinfo.endTime;
    //localStorage.getItem("endTime");

    var UdpatePreContentValue = skdinfo.content;
    //localStorage.getItem("content");

    //test용 프린트
    console.log(UpdatePreTitleValue);
    console.log(UpdatePreStartDate);
    console.log(UpdatePreStartTime);
    console.log(skdOriginEndDate);
    console.log(skdOriginEndTime);
    console.log(UdpatePreContentValue);

    //기존 상세내역에 있었던 내용을 input에 넣기
    skdUpdateTitle.attr("value", UpdatePreTitleValue);
    skdUpdateStartDate.val(UpdatePreStartDate);
    skdUpdateStartTime.val(UpdatePreStartTime);
    skdUpdateEndDate.val(skdOriginEndDate);
    skdUpdateEndTime.val(skdOriginEndTime);
    skdUpdateContent.val(UdpatePreContentValue);
    // skdUpdateType.val(UdpatePreTypeValue);
  });

  $("[class*='modifySkdSubmit']")
    .off("click")
    .on("click", function () {
      console.log("수정클릭" + backSkdModify);
      console.log("skdType:" + skdUpdateTypeValue);
      console.log("title: " + skdUpdateTitle.val());
      // alert(
      //   skdUpdateStartTime.val() +
      //     "-" +
      //     skdUpdateEndDate.val() +
      //     ", " +
      //     skdUpdateEndTime.val()
      // );
      // console.log(
      //   "starttime:" + skdUpdateStartDate //.val() + " " + skdUpdateStartTime.val()
      // );
      // console.log(
      // "endtime" + skdUpdateEndDate//.val() + " " + skdUpdateEndTime.val()
      // );
      // console.log("share" + teamOrPersonalOption);

      // let checker = $._data($("[class*='modifySkdSubmit']")[0], "events");
      // console.log(checker);
      //일정번호를 기준으로 수정하는 것이기 때문에 skd_no가 반드시 필요하다
      //var teamOrPersonalOption = localStorage.getItem("share");
      alert("teamOrPersonalOption=" + teamOrPersonalOption);

      if (teamOrPersonalOption.trim() == "p") {
        console.log("----수정 클릭시 전달데이터 ---");
        let jsObj = {
          skdType: skdUpdateTypeValue,
          skdTitle: skdUpdateTitle.val(),
          skdContent: skdUpdateContent.val(),
          skdStartDate:
            skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),
          skdEndDate: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
          skdShare: teamOrPersonalOption,
        };
        console.log(jsObj);
        console.log("---------------------");
        let data = JSON.stringify(jsObj);

        $.ajax({
          url: backSkdModify,
          method: "PUT",
          transformRequest: [null],
          transformResponse: [null],
          jsonpCallbackParam: "callback",
          headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json;charset=utf-8",
          },
          data: data,

          success: function () {
            window.alert("일정이 변경되었습니다");

            //scheduleMenu로 돌아가는 트리거 이벤트
            //loadSchedule();
            //location.reload();
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

        //팀일정일 경우
      } else if (teamOrPersonalOption == "t") {
        $.ajax({
          url: backSkdModify,
          method: "PUT",
          transformRequest: [null],
          transformResponse: [null],
          jsonpCallbackParam: "callback",
          headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json;charset=utf-8",
          },
          data: JSON.stringify({
            skdType: skdUpdateTypeValue,
            skdTitle: skdUpdateTitle.val(),
            skdContent: skdUpdateContent.val(),
            skdStartDate:
              skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),
            skdEndDate: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
            skdShare: teamOrPersonalOption,
          }),
          success: function () {
            window.alert("2일정이 변경되었습니다");
            //loadSchedule();
            // location.reload();
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
        //e.preventDefault();
      }
      return false;
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
      console.log(responseData);
      init(responseData, "내용검색");
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
      init(responseData, "날짜검색");
    },
  });
});
