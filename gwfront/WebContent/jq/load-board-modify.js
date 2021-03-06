$(function () {
  //현재 로그인한 사원의 아이디 객체
  var loginInfoIdObj = document.querySelector(
    "div.profileDropdown span.loginId"
  );
  var $content = $("main.content");
  //기존의 게시글 제목값
  var originBdTitleInModi = localStorage.getItem("bdTitle");
  //기존의 게시글 내용값
  var originBdContentInModi = localStorage.getItem("bdContent");
  //게시글 번호값
  var bdTargetNoInModi = localStorage.getItem("bdNumber");

  //게시글 수정 form 객체
  var modifyBdFormObj = document.querySelector("form#modifyBdForm");
  //게시글 제목 수정 input 객체
  var titleObjInModi = document.querySelector("input[name=title]");
  //게시글 내용 수정 textarea 객체 - summernote
  var contentObjInModi = document.querySelector("div.note-editable");
  //게시글 내용 textarea placeholder 객체
  var contentPlaceHolderObj = document.querySelector("div.note-placeholder");

  //게시글 내용이 있다면, placeholder 없애기
  if (
    originBdContentInModi.innerHTML != "" ||
    originBdContentInModi.innerHTML != null
  ) {
    contentPlaceHolderObj.innerHTML = "";
  }

  //게시글 제목 수정 input 객체에 기존 게시글 제목값 대입
  titleObjInModi.value = originBdTitleInModi;
  //게시글 내용 수정 input 객체에 기존 게시글 내용값 대입
  contentObjInModi.innerHTML = originBdContentInModi;

  //게시글 수정 시 사용할 backurl
  var backurlModiBdDetail = "http://localhost:8888/gwback/board/modifybd";
  //관리자가 게시글 수정시 사용할 backurl
  var adminbackurlModiBdDetail = "http://localhost:8888/gwback/admin/modifybd";
  //게시글 수정 form submit 이벤트 핸들러

  var modifyUrl =
    loginInfoIdObj.innerHTML == "admin"
      ? adminbackurlModiBdDetail + "/" + bdTargetNoInModi
      : backurlModiBdDetail + "/" + bdTargetNoInModi;
  var modifyReload =
    loginInfoIdObj.innerHTML == "admin"
      ? "notice-detail.html"
      : "board-detail.html";
  function modifyBdSubmitHandler(e) {
    $.ajax({
      url: modifyUrl,
      method: "PUT",
      transformRequest: [null],
      transformResponse: [null],
      jsonpCallbackParam: "callback",
      headers: {
        Accept: "application/json, text/plain, */*",
        "Content-Type": "application/json;charset=utf-8",
      },
      // data: data,
      timeout: {},
      data: JSON.stringify({
        // modiBdTargetNo: bdTargetNoInModi,
        bdTitle: titleObjInModi.value,
        bdContent: contentObjInModi.innerText,
      }),
      success: function () {
        //제목이 입력되지 않으면 수정 x
        if (titleObjInModi.value == "" || titleObjInModi.value == null) {
          alert("제목이 입력되지 않았습니다");
        } else {
          alert("게시글이 변경되었습니다");
          //게시글 수정 후 재로딩
          var href = modifyReload;
          //"notice-detail.html";
          $content.load(href, function (responseTxt, statusTxt, xhr) {
            if (statusTxt == "error")
              alert("Error: " + xhr.status + ": " + xhr.statusText);
          });
          // $(
          //   '#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li > a[href="board-detail.html"]'
          // ).trigger("click");
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

    e.preventDefault();
  }

  //form 객체 submit 이벤트 등록
  modifyBdFormObj.addEventListener("submit", modifyBdSubmitHandler);
});
