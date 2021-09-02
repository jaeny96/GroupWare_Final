$(function () {
  //현재 로그인한 사원의 아이디 객체
  var loginInfoIdObj = document.querySelector(
    "div.profileDropdown span.loginId"
  );

  //현재 로그인한 사원의 이름 객체
  var loginInfoNameObj = document.querySelector(
    "div.profileDropdown span.loginName"
  );

  //게시글 작성란 form 객체
  var addBdFormObj = document.querySelector("form#addBdForm");
  //게시글 제목 input 객체
  var titleObjInAdd = document.querySelector("input[name=title]#insertTitle");
  //게시글 내용 작성 객체 - summvernote
  var contentObjInAdd = document.querySelector("div.note-editable");

  //board-register 시 사용할 backurl
  //게시글 작성하기
  //var backurlAddBoard = "/back/addboard";
  var backurlAddBoard = "http://localhost:8888/gwback/board/addboard";
  //관리자 공지사항 작성하기
  var adminbackurlAddBoard = "http://localhost:8888/gwback/admin/addboard";
  //파일 업로드하기
  var backurlFileUpLoadInBd =
    "http://localhost:8888/gwback/board/fileuploadinbd";

  //게시글 등록 submit 이벤트 핸들러
  function addBdFormSubmitHandler(e) {
    if (loginInfoIdObj.innerHTML == "admin") {
      $.ajax({
        url: adminbackurlAddBoard,
        method: "post",
        transformRequest: [null],
        transformResponse: [null],
        jsonpCallbackParam: "callback",
        headers: {
          Accept: "application/json, text/plain, */*",
          "Content-Type": "application/json;charset=utf-8",
        },
        data: JSON.stringify({
          bdWriter: loginInfoNameObj.innerText,
          bdWriterId: loginInfoIdObj.innerText,
          bdTitle: titleObjInAdd.value,
          bdContent: contentObjInAdd.innerText,
          bdAdmin: 0,
        }),
        success: function () {
          //제목이 작성되지 않았으면 등록 x
          if (titleObjInAdd.value == "" || titleObjInAdd.value == null) {
            alert("제목이 입력되지 않았습니다");
          } else {
            alert("게시글이 등록되었습니다");
            //게시글 목록 페이지로 이동
            $(
              "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li:nth-child(1) > a"
            ).trigger("click");
          }
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
    } else {
      $.ajax({
        url: backurlAddBoard,
        method: "post",
        transformRequest: [null],
        transformResponse: [null],
        jsonpCallbackParam: "callback",
        headers: {
          Accept: "application/json, text/plain, */*",
          "Content-Type": "application/json;charset=utf-8",
        },
        data: JSON.stringify({
          bdWriter: loginInfoNameObj.innerText,
          bdWriterId: loginInfoIdObj.innerText,
          bdTitle: titleObjInAdd.value,
          bdContent: contentObjInAdd.innerText,
          bdAdmin: 1,
        }),
        success: function () {
          //제목이 작성되지 않았으면 등록 x
          if (titleObjInAdd.value == "" || titleObjInAdd.value == null) {
            alert("제목이 입력되지 않았습니다");
          } else {
            alert("게시글이 등록되었습니다");
            //게시글 목록 페이지로 이동
            $(
              "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li:nth-child(1) > a"
            ).trigger("click");
          }
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

    //완성하지 못한 업로드 부분
    // $.ajax({
    //   url: backurlFileUpLoadInBd,
    //   method: "post",
    //   data: {
    //     addBdWriter: loginInfoNameObj.innerText,
    //     addBdWriterId: loginInfoIdObj.innerText,
    //     addBdTitle: titleObjInAdd.value,
    //     addBdContent: contentObjInAdd.innerText,
    //   },
    //   success: function () {
    //     if (titleObjInAdd.value == "" || titleObjInAdd.value == null) {
    //       alert("제목이 입력되지 않았습니다");
    //     } else {
    //       alert("게시글이 등록되었습니다");
    //       $(
    //         'div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li>a[href="board.html"]'
    //       ).trigger("click");
    //     }
    //   },
    //   error: function (request, status, error) {
    //     alert(
    //       "code:" +
    //         request.status +
    //         "\n" +
    //         "message:" +
    //         request.responseText +
    //         "\n" +
    //         "error:" +
    //         error
    //     );
    //   },
    // });
    e.preventDefault();
  }

  //form 객체 submit 이벤트 등록
  addBdFormObj.addEventListener("submit", addBdFormSubmitHandler);
});
