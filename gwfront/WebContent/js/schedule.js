var calendar = null;
var $content = $("div.wrapper>div.main>main.content");

function loadSchedule() {
  var href = "schedule.html";
  $content.load(href, function (responseTxt, statusTxt, xhr) {
    if (statusTxt == "error")
      alert("Error: " + xhr.status + ": " + xhr.statusText);
  });
}

/**
 * 캘린더 함수 시작
 */
$(function () {
  var Calendar = FullCalendar.Calendar;
  var calendarEl = document.getElementById("calendar");

  /**
   * 캘린더 설정
   */
  calendar = new Calendar(calendarEl, {
    initialView: "dayGridMonth",
    locale: "ko",
    height: "100%",
    editable: true,
    eventLimit: true /*달력상에 셀 크기보다 많은 이벤트가 등록되어 있는 경우 more로 표기*/,
    dayMaxEventRows: true,
    navLinks: true,
    selectable: true,

    navLinkWeekClick: function (weekStart, jsEvent) {
      console.log("week start", weekStart.toISOString());
      console.log("coords", jsEvent.pageX, jsEvent.pageY);
    },
    events: function (info, successCallback, failureCallback) {
      var loginedId = localStorage.getItem("loginInfo");
      var loginedDept = loginedId.substring(0, 3);
      var id = loginedId;
      var dept = loginedDept;
      /**
       * 캘린더에 일정 보여주기 type = 팀일정, 개인일정
       */
      $.ajax({
        method: "GET",
        transformRequest: [null],
        transformResponse: [null],
        url: "/gwback/schedule/allSkd",
        jsonpCallbackParam: "callback",
        data: "",
        success: function (result) {
          console.log(result);
          var events = [];
          if (result != null) {
            $.each(result, function (i, e) {
              var type = $.trim(e.skdShare);
              //컴퓨터마다 t와 p에 띄어쓰기가 있거나 없어서 trim으로 잘라줌
              var enddate = e.skdEndDate;
              var startdate = moment(e.skdStartDate).format("YYYY-MM-DD HH:mm");
              var enddate = moment(enddate).format("YYYY-MM-DD HH:mm");
              if (type == "t") {
                //캘린더가 가지고 있는 객체에 DB값 세팅
                events.push({
                  title: e.skdTitle,
                  start: startdate,
                  end: enddate,
                  resource: type,
                  id: e.skdNo,
                  extendedProps: { content: e.skdContent },
                  color: "#28a745",
                });
              } else if (type == "p") {
                events.push({
                  title: e.skdTitle,
                  start: startdate,
                  end: enddate,
                  resource: type,
                  id: e.skdNo,
                  extendedProps: { content: e.skdContent },
                  color: "#ffc107",
                });
              }
              console.log(events);
            });
            successCallback(events);
          }
        },
      });
    },
    customButtons: {
      /**
       * 일정 추가 버튼
       */
      addeventButton: {
        text: "일정 추가",
        id: "modalInputBtn",
        click: function () {
          //modal 띄우기
          const modal = document.getElementById("skdInput");
          const closeBtn = document.querySelector("button.cancel");
          modal.classList.remove("hidden");

          //모달 닫기
          $(closeBtn).on("click", function () {
            modal.classList.add("hidden");
          });

          //팀캘린더, 개인캘린더 지정
          var teamOrPersonalOption = "p";
          var skdInputShare = $('input[name="radio-3"]');
          skdInputShare.change(function () {
            //팀 = t, 개인 = p로 인식하도록 하는 함수
            teamOrPersonalOption = this.value;
            console.log(teamOrPersonalOption);
          });

          //업무, 출장 등 스케쥴 종류 지정
          var skdInputTypeValue = "업무";
          var skdInputTypeObj = $("#skdType");
          skdInputTypeObj.change(function () {
            //드롭다운에서 선택한 값으로 인식하도록 하는 함수
            console.log(this.value);
            skdInputTypeValue = this.value + "";
          });

          //저장버튼 클릭이벤트
          $("button.skdInsertSaveBtn").on("click", function (e) {
            //var selectOption = document.getElementById("skdType");

            //schedule.html의 스케쥴 입력 모달 창의 input 객체를 각 변수에 주입
            var skdInputTitle = $("#input_title"); //제목
            var skdInputContent = $("#input_content"); //내용
            var skdInputStartDate = $("#start_date"); //시작날짜
            var skdInputStartTime = $("#start_time"); //시작시간
            var skdInputEndDate = $("#end_date"); //종료날짜
            var skdInputEndTime = $("#end_time"); //종료시간

            //시간을 입력하지 않았을 경우 넘어가지 못함
            if (skdInputStartTime.val() == "" || skdInputEndTime.val() == "") {
              alert("시간을 입력하세요.");
              return false;
              //종료일이 시작일보다 먼저일 경우 넘어가지 못함
            } else if (
              new Date(skdInputEndDate.val()) -
                new Date(skdInputStartDate.val()) <
              0
            ) {
              alert("종료일이 시작일보다 먼저입니다.");
              return false;
            } //이 두 단계를 거쳐야 스케쥴 입력이 가능
            else {
              if (teamOrPersonalOption == "p") {
                //개인 일정일 경우
                //일정 form 받아오기
                var skdInsertObj = document.querySelector(
                  "#skdInput > div.modal_content > div > form"
                );
                console.log("스케쥴 입력 객체" + skdInsertObj);
                //일정 저장 버튼
                // var skdInsertBtn = skdInsertObj.querySelector(
                //   "button.skdInsertSaveBtn"
                // );

                //addschedule servlet으로  전송
                /**
                 * title : 제목
                 * content : 내용
                 * start : 일정 시작 시간
                 * end: 일정 종료 시간
                 * backgroundColor : 개인 or 팀 일정에 따른 색깔 지정
                 * calendarType : 출장, 업무 등 일정종류
                 * teamOrPersonal : 개인 or 팀 일정
                 */
                var skdInsertUrl = "/gwback/schedule/addSkd";

                $.ajax({
                  url: skdInsertUrl,
                  method: "POST",
                  transformRequest: [null],
                  transformResponse: [null],
                  jsonpCallbackParam: "callback",
                  headers: {
                    Accept: "application/json, text/plain, */*",
                    "Content-Type": "application/json;charset=utf-8",
                  },
                  data: JSON.stringify({
                    skdType: skdInputTypeValue,
                    skdTitle: skdInputTitle.val(),
                    skdContent: skdInputContent.val(),
                    skdStartDate:
                      skdInputStartDate.val() + " " + skdInputStartTime.val(),
                    skdEndDate:
                      skdInputEndDate.val() + " " + skdInputEndTime.val(),
                    skdShare: teamOrPersonalOption,
                  }),

                  success: function () {
                    alert("일정이 추가되었습니다");
                    //location.reload();
                    loadSchedule();
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

                //팀 일정일 경우
              } else if (teamOrPersonalOption == "t") {
                var skdInsertObj = document.querySelector(
                  "#skdInput > div.modal_content > div > form"
                ); //
                //  console.log(skdInsertObj);
                //일정 저장 버튼
                var skdInsertBtn = skdInsertObj.querySelector(
                  "button.skdInsertSaveBtn"
                );
                console.log("team type" + skdInputTypeValue);

                var skdInsertUrl = "/gwback/schedule/addSkd";
                console.log(skdInputTitle.val() + skdInputContent.val());

                $.ajax({
                  url: skdInsertUrl,
                  method: "POST",
                  transformRequest: [null],
                  transformResponse: [null],
                  jsonpCallbackParam: "callback",
                  headers: {
                    Accept: "application/json, text/plain, */*",
                    "Content-Type": "application/json;charset=utf-8",
                  },
                  data: JSON.stringify({
                    skdType: skdInputTypeValue,
                    skdTitle: skdInputTitle.val(),
                    skdContent: skdInputContent.val(),
                    skdStartDate:
                      skdInputStartDate.val() + " " + skdInputStartTime.val(),
                    skdEndDate:
                      skdInputEndDate.val() + " " + skdInputEndTime.val(),
                    skdShare: teamOrPersonalOption,
                  }),

                  success: function () {
                    alert("일정이 추가되었습니다");
                    //location.reload();
                    loadSchedule();
                    // console.log(
                    //   $(
                    //     "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li:nth-child(6) > a"
                    //   )
                    // );
                    // $(
                    //   "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li:nth-child(6) > a"
                    // ).trigger("click");
                    // $(function () {
                    //   $("#scheduleMenu")
                    //     .click(function () {
                    //       this.click();
                    //     })
                    //     .click();
                    // });
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
                e.preventDefault(); //이걸 해줘야 하나?
                // $("#demo-calendar").fullCalendar('addEventSource', JSON, true);
              }
            }
          });
        },
      },
      /**
       * 전체캘린더 버튼 클릭시 전체 일정 보여주기
       */
      allSchedule: {
        text: "전체 캘린더",
        click: function (info, successCallback, failureCallback) {
          var loginedId = localStorage.getItem("loginInfo");
          var loginedDept = loginedId.substring(0, 3);
          var id = loginedId;
          var dept = loginedDept;
          $.ajax({
            url: "/gwback/schedule/allSkd",
            method: "GET",
            transformRequest: [null],
            transformResponse: [null],
            jsonpCallbackParam: "callback",
            data: "",
            success: function (result) {
              calendar.removeAllEventSources();
              var events = [];
              if (result != null) {
                $.each(result, function (i, e) {
                  // var type = e.skd_share;
                  var type = $.trim(e.skdShare);
                  var startdate = moment(e.skdStartDate).format(
                    "YYYY-MM-DD HH:mm"
                  );
                  var enddate = moment(e.skdEndDate).format("YYYY-MM-DD HH:mm");
                  if (type == "t") {
                    events.push({
                      title: e.skdTitle,
                      start: startdate,
                      end: enddate,
                      resource: type,
                      color: "#28a745",
                    });
                  } else if (type == "p") {
                    events.push({
                      title: e.skdTitle,
                      start: startdate,
                      end: enddate,
                      resource: type,
                      color: "#ffc107",
                    });
                  }
                  console.log("전체버튼" + events);
                });
                calendar.addEventSource(events);
              }
            },
          });
        },
      },
      /**
       * 공유캘린더 버튼 클릭시 팀 일정 보여주기
       */
      teamSchedule: {
        text: "공유 캘린더",
        click: function () {
          var loginedId = localStorage.getItem("loginInfo");
          var loginedDept = loginedId.substring(0, 3);
          var dept = loginedDept;
          $.ajax({
            url: "/gwback/schedule/skdTeam",
            method: "GET",
            transformRequest: [null],
            transformResponse: [null],
            jsonpCallbackParam: "callback",
            data: "",
            success: function (result) {
              calendar.removeAllEventSources();
              var events = [];
              if (result != null) {
                $.each(result, function (i, e) {
                  var enddate = e.skdEndDate;
                  var startdate = moment(e.skdStartDate).format(
                    "YYYY-MM-DD HH:mm"
                  );
                  var enddate = moment(enddate).format("YYYY-MM-DD HH:mm");
                  events.push({
                    title: e.skdTitle,
                    start: startdate,
                    end: enddate,
                    color: "#28a745",
                  });
                  //console.log("팀스케줄" + events);
                });
                calendar.addEventSource(events);
              }
            },
          });
        },
      },
      /**
       * 내캘린더 버튼 클릭시 개인 일정 보여주기
       */
      personalSchedule: {
        text: "내 캘린더",
        click: function () {
          var loginedId = localStorage.getItem("loginInfo");
          var id = loginedId;

          $.ajax({
            url: "/gwback/schedule/skdPersonal",
            method: "GET",
            transformRequest: [null],
            transformResponse: [null],
            jsonpCallbackParam: "callback",
            data: "",
            success: function (result) {
              calendar.removeAllEventSources();
              var events = [];
              if (result != null) {
                $.each(result, function (i, e) {
                  var enddate = e.skdEndDate;
                  var startdate = moment(e.skdStartDate).format(
                    "YYYY-MM-DD HH:mm"
                  );
                  var enddate = moment(enddate).format("YYYY-MM-DD HH:mm");
                  events.push({
                    title: e.skdTitle,
                    start: startdate,
                    end: enddate,
                    color: "#ffc107",
                  });
                  // console.log(events);
                });
                calendar.addEventSource(events);
              }
            },
          });
        },
      },
      weekSchedule: {
        text: "주간",
        class: "weekSchedule",
      },
    },
    headerToolbar: {
      left: "addeventButton allSchedule teamSchedule personalSchedule",
      center: "title",
      right: "dayGridWeek,dayGridMonth today prev,next",
    },
    //extendedProp 적용
    eventDidMount: function (info) {},
    eventClick: function (info) {
      //일정 클릭했을 때
      // localStorage.setItem("skdNo", info.event.id);
      console.log("a " + info.event.id);

      createModal("skdDetail", info.event.id);
    },
  }); //New calendar끝
  calendar.render();
  //모달 관련
  //시작 날짜를 현재 날짜로
  document.getElementById("start_date").value = new Date()
    .toISOString()
    .substring(0, 10);
  //종료 날짜를 현재 날짜로
  document.getElementById("end_date").value = new Date()
    .toISOString()
    .substring(0, 10);

  //시작 시간을 현재 시간으로
  document.getElementById("start_time").value = new Date(
    new Date()
  ).toTimeString();
  //종료 시간을 현재 시간으로
  document.getElementById("end_time").value = new Date(
    new Date()
  ).toTimeString();

  //기간으로 검색하기, 제목내용으로 검색하기 창 설정
  //var skdSearchObj = document.getElementById("skdSearch");
  //검색 드롭다운메뉴
  //var categoryObj = document.querySelector("div.dropdown-menu");
  //기간으로 검색하기, 제목내용으로 검색하기 창 설정
  var $searchBtnSkdObj = $("button#searchBtnSKD");
  //검색 드롭다운메뉴
  var $categoryObj = $("div#skdSearchDropDown");

  //기간으로검색 id
  var $searchPeriod = $("#skdCategoryPeriod");
  //제목으로 검색 id
  var $searchTitle = $("#skdCategoryTitle");

  //function categoryHandler() {
  // if (e.target.id == "skdCategoryPeriod") {
  //   createModal("skdSearchPeriod");
  // } else if (e.target.id == "skdCategoryTitle") {
  //   console.log("내용검색" + e.target.id);
  //   createModal("skdSearchTitle");
  // }
  $searchPeriod.on("click", function () {
    createSearchModal("skdSearchPeriod");
  });
  $searchTitle.on("click", function () {
    createSearchModal("skdSearchTitle");
  });
  //}

  $searchBtnSkdObj.click(function () {
    $categoryObj.slideToggle(300);
  });

  // function initSearchModal() {
  //   $categoryObj.click(function () {
  //     categoryHandler();
  //   });
  // }

  // initSearchModal();
  ////////////////////////////////////////////일정검색 드롭다운메뉴
  //modal 만드는 함수
  //파라미터 : class="modal" 의 id

  /**
   *
   * @param {*} id
   * 모달창 제작 함수
   */

  function createSearchModal(id) {
    var modal = document.getElementById(id);
    modal.classList.remove("hidden"); //모달열기

    //모달 버튼
    var closeBtn = modal.querySelector("button.cancel");
    var overlay = modal.querySelector(".modal_overlay");
    var deleteBtn = modal.querySelector("button.deleteBtn");
    var xBoxBtn = modal.querySelector("button.xBox");
    var modifyBtn = modal.querySelector("button.modifyBtn");
    var modifySubmitBtnObj = modal.querySelector("button.modifySubmit");

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
    //엑스박스 닫기
    if (xBoxBtn != null) {
      xBoxBtn.addEventListener("click", closeModal);
    }
    //취소, 닫기 버튼
    if (closeBtn != null) {
      closeBtn.addEventListener("click", closeModal);
    }
    //수정 버튼
    if (modifyBtn != null) {
      modifyBtn.addEventListener("click", function () {
        modal.classList.add("hidden");
        //수정 버튼 클릭 시 수정 모달 뜸
        var skdno = $("div.skdNo").html(); //omj
        createModal("skdModifyDetail", skdno); //omj
      });
    }
  }

  function createModal(id, skdno) {
    //로컬스토리지에 있는 skdNo 불러오기
    //const skdnono = localStorage.getItem("skdNo");
    $("div.skdNo").html(skdno);
    console.log(skdno + "현재skdno");

    var modal = document.getElementById(id);
    modal.classList.remove("hidden"); //모달열기

    //모달 버튼
    var closeBtn = modal.querySelector("button.cancel");
    var overlay = modal.querySelector(".modal_overlay");
    var deleteBtn = modal.querySelector("button.deleteBtn");
    var xBoxBtn = modal.querySelector("button.xBox");
    var modifyBtn = modal.querySelector("button.modifyBtn");
    var modifySubmitBtnObj = modal.querySelector("button.modifySubmit");

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
    //엑스박스 닫기
    if (xBoxBtn != null) {
      xBoxBtn.addEventListener("click", closeModal);
    }
    //취소, 닫기 버튼
    if (closeBtn != null) {
      closeBtn.addEventListener("click", closeModal);
    }
    //수정 버튼
    if (modifyBtn != null) {
      modifyBtn.addEventListener("click", function () {
        modal.classList.add("hidden");
        //수정 버튼 클릭 시 수정 모달 뜸
        var skdno = $("div.skdNo").html(); //omj
        createModal("skdModifyDetail", skdno); //omj
      });
    }
    console.log(skdno + "2");
    $.ajax({
      url: "/gwback/schedule/skdDetail/" + skdno,
      method: "GET",
      transformRequest: [null],
      transformResponse: [null],
      jsonpCallbackParam: "callback",
      data: "",
      success: function (responseData) {
        $(responseData).each(function (i, e) {
          titleValue.innerHTML = e.skdTitle;
          typeValue.innerHTML = e.skdType.skdType;
          StartTimeValue.innerHTML = e.skdStartDate;
          EndTimeValue.innerHTML = e.skdEndDate;
          ContentValue.innerHTML = e.skdContent;

          //ms 2021-07-31
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
          //location.reload();
          loadSchedule();
        },
      });
      // e.preventDefault();
    });
    // const editskdNo = localStorage.getItem("skdNo");
    // console.log(editskdNo + "수정skdno");
    //경로
    var backSkdModify = "/gwback/schedule/modify/" + skdno;
    //localStorage.getItem("skdNo");

    //var modifySkdFormObj = $("#modifySkdContent");
    var modifySkdSubmitBtn = $("button.modifySkdSubmit"); //수정 버튼
    var skdUpdateTypeObj = $("#skdUpdateTypeSelect"); //일정 종류 변수

    var skdUpdateTypeValue = "업무"; // 일정 종류 변경 함수
    skdUpdateTypeObj.change(function () {
      console.log(this.value);
      skdUpdateTypeValue = this.value + "";
    });

    //변경 내용을 담은 input 객체를 변수에 할당
    var skdUpdateTitle = $("#update_title"); //제목
    var skdUpdateContent = $("#input_content_update"); //내용
    var skdUpdateStartDate = $("#start_date_update"); // 일정 시작 날자
    var skdUpdateStartTime = $("#start_time_update"); // 일정 시작 시간
    var skdUpdateEndDate = $("#end_date_update"); //종료날짜
    var skdUpdateEndTime = $("#end_time_update"); //종료시간
    var skdUpdateShare2 = $('input[name="radio-2"]'); //일정 종류인데.. 적용이 안 됨 ㅠㅠ

    //skd_no, 일정번호를 localStorage에서 받아온다
    //var currentSkdNo = localStorage.getItem("skdNo");

    //팀, 개인 일정 선택
    var teamOrPersonalOption = "p";
    skdUpdateShare2.change(function () {
      console.log(this.value);
      teamOrPersonalOption = this.value;
      console.log(teamOrPersonalOption);
    });

    //수정 버튼 클릭 시 이벤트
    $("#skdModifyBtn").click(function () {
      // const clickedskdNo = localStorage.getItem("skdNo");

      //일정 상세 내역 클릭 시 localStorage에 저장된 내역들을 불러와 변수에 지정
      var UpdatePreTitleValue = localStorage.getItem("title");
      var UpdatePreStartDate = localStorage.getItem("startDate");
      var UpdatePreStartTime = localStorage.getItem("startTime");
      var skdOriginEndDate = localStorage.getItem("endDate");
      var skdOriginEndTime = localStorage.getItem("endTime");
      var UdpatePreContentValue = localStorage.getItem("content");

      //test용 프린트
      // console.log(UpdatePreTitleValue);
      // console.log(UpdatePreStartDate);
      // console.log(UpdatePreStartTime);
      // console.log(skdOriginEndDate);
      // console.log(skdOriginEndTime);
      // console.log(UdpatePreContentValue);

      //기존 상세내역에 있었던 내용을 input에 넣기
      skdUpdateTitle.attr("value", UpdatePreTitleValue);
      skdUpdateStartDate.val(UpdatePreStartDate);
      skdUpdateStartTime.val(UpdatePreStartTime);
      skdUpdateEndDate.val(skdOriginEndDate);
      skdUpdateEndTime.val(skdOriginEndTime);
      skdUpdateContent.val(UdpatePreContentValue);
      // skdUpdateType.val(UdpatePreTypeValue);
    });

    //저장 버튼 클릭 시 이벤트
    //function modifySkdSubmitHandler(e) {

    $("[class*='modifySkdSubmit']")
      .off("click")
      .on("click", function () {
        // let checker = $._data($("[class*='modifySkdSubmit']")[0], "events");
        // console.log(checker);
        //일정번호를 기준으로 수정하는 것이기 때문에 skd_no가 반드시 필요하다
        if (teamOrPersonalOption == "p") {
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
              window.alert("일정이 변경되었습니다");

              //scheduleMenu로 돌아가는 트리거 이벤트
              loadSchedule();
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
              loadSchedule();
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
          //e.preventDefault();
        }
      });
  }

  //상세내역 모달에 데이터
  //일정 상세내역
  var shareObj = document.getElementById("skdDetailShare");
  var shareValue = shareObj.querySelector("td.skdDetailInputData");
  //console.log(shareValue);
  var typeObj = document.getElementById("skdDetailType");
  var typeValue = typeObj.querySelector("td.skdDetailInputData");
  var titleObj = document.getElementById("skdDetailTitle");
  var titleValue = titleObj.querySelector("td.skdDetailInputData");
  var StartTimeObj = document.getElementById("skdDetailStartTime");
  var StartTimeValue = StartTimeObj.querySelector("td.skdDetailInputData");
  var EndTimeObj = document.getElementById("skdDetailEndTime");
  var EndTimeValue = EndTimeObj.querySelector("td.skdDetailInputData");
  var ContentObj = document.getElementById("skdDetailContent");
  var ContentValue = ContentObj.querySelector("td.skdDetailInputData");
});
