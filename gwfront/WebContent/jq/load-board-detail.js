$(function () {
  var $content = $("main.content");
  //뒤로가기 버튼 객체
  var boardPageGoBtnObj = document.querySelector("button.boardPageGoBtn");
  //로그인한 사원의 아이디 객체
  var loginInfoIdObj = document.querySelector(
    "div.profileDropdown span.loginId"
  );
  //로그인한 사원의 이름 객체
  var loginInfoNameObj = document.querySelector(
    "div.profileDropdown span.loginName"
  );

  //게시글 상세 제목 전체를 감싸고 있는 div 객체
  var bdDetailObj = document.querySelector("div.bdDetailTitle");
  //게시글 제목 객체
  var detailTitleObj = bdDetailObj.querySelector("h2");
  //게시글 정보 감싸고 있는 div 객체
  var detailInfoObj = bdDetailObj.querySelector("div.flex-grow-1");
  //게시글 작성자 객체
  var infoWriterObj = detailInfoObj.querySelector("small.bdDetailWriter");
  //게시글 작성날짜 객체
  var infoDateObj = detailInfoObj.querySelector("small.bdDetailDate");
  //게시글 상세 내용 객체
  var detailContentObj = document.querySelector("div.bdDetailContent");
  //게시글 수정,삭제 버튼 그룹 객체
  var bdBtnGroupObj = document.querySelector("div#bdBtnGroupModify");
  //댓글 작성란 감싸고 있는 div 객체
  var cmCardBodyObj = document.querySelector("div.cmCardBody");
  //댓글 작성자 객체
  var cmWriterObj = document.querySelector("h5.currentLoginId");
  //댓글 작성란 textarea 객체
  var cmTextAreaObj = document.querySelector("textarea");
  //댓글 등록 form 객체
  var cmRegisterFormObj = document.querySelector("div.col-md-12 form");

  //해당 게시글의 게시글 번호 값 저장해놓은 변수
  var bdDetailBdNo = localStorage.getItem("bdNumber");
  //게시글 제목 값 들어갈 변수
  var bdDetailTitle;
  //게시글 작성자 값 들어갈 변수
  var bdDetailWriter;
  //게시글 작성자 아이디 값 들어갈 변수
  var bdDetailWriterId;
  //게시글 작성일 값 들어갈 변수
  var bdDetailDate;
  //게시글 상세 내용 값 들어갈 변수
  var bdDetailContent;

  //현재 로그인한 사원의 이름 값 대입
  var currentLoginEmp = loginInfoNameObj.innerText;
  //현재 로그인한 사원의 아이디 값 대입
  var currentLoginId = loginInfoIdObj.innerText;

  //댓글 번호 값 들어갈 arr
  var cmNo = new Array();
  //댓글 작성자 값 들어갈 arr
  var cmWriter = new Array();
  //댓글 작성자 아이디 값 들어갈 arr
  var cmWrtierId = new Array();
  //댓글 작성일 값 들어갈 arr
  var cmDate = new Array();
  //댓글 내용 값 들어갈 arr
  var cmContent = new Array();

  //댓글 삭제 시 사용할 backurl
  //var backurlDeleteCm = "/back/removeboardcomment";
  var backurlDeleteCm = "http://localhost:8888/gwback/boardcomment/removecm";

  //뒤로가기 버튼 클릭 시 게시판 메뉴 클릭 trigger 이벤트 발생
  function boardPageGoClickHandler() {
    if (currentLoginId == "admin") {
      $(
        "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li:nth-child(1) > a"
      ).trigger("click");
    } else {
      $(
        "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li:nth-child(4) > a"
      ).trigger("click");
    }
  }

  //뒤로가기 버튼 클릭 시 발생 이벤트 등록
  boardPageGoBtnObj.addEventListener("click", boardPageGoClickHandler);

  //댓글 삭제 클릭 핸들러
  function cmDeleteClickHandler(e) {
    $.ajax({
      url: backurlDeleteCm + "/" + bdDetailBdNo + "/" + e.target.id,
      method: "DELETE",
      transformRequest: [null],
      transformResponse: [null],
      jsonpCallbackParam: "callback",
      headers: {
        Accept: "application/json, text/plain, */*",
      },
      data: "",
      timeout: {},
      success: function (responseData) {
        "board-detail.html";
        alert("댓글이 삭제되었습니다!");
        //댓글 삭제 후 재로딩
        if (currentLoginId == "admin") {
          var href = "notice-detail.html";
        } else {
          var href = "board-detail.html";
        }
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        // $(
        //   '#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li > a[href="board-detail.html"]'
        // ).trigger("click");
      },
    });
    e.preventDefault();
  }

  //db에 저장되어 있는 댓글 객체 생성하는 함수
  function createCmElement(i) {
    //댓글 감싸는 div 객체 생성
    var cmBodyDiv = document.createElement("div");
    //div객체 스타일 생성
    cmBodyDiv.setAttribute("class", "d-flex align-items-start cmBody mb-2");

    //프로필 이미지 넣을 img 태그 생성
    var profileImg = document.createElement("img");
    //임시 프로필 이미지 등록
    profileImg.setAttribute(
      "src",
      " http://localhost:8888/gwfront/img/avatars/" + cmWrtierId[i] + ".jpg"
    );
    //프로필 크기 설정
    profileImg.setAttribute("width", "36");
    profileImg.setAttribute("height", "36");
    //프로필 스타일 생성
    profileImg.setAttribute("class", "rounded-circle mr-2 mb-2");
    //프로필 이미지 등록 못했을 때 나타낼 문구
    profileImg.setAttribute("alt", cmWriter[i]);

    //댓글 내용 들어갈 div 객체
    var cmContentDiv = document.createElement("div");
    //댓글 내용 스타일 생성
    cmContentDiv.setAttribute("class", "flex-grow-1");

    //댓글 삭제 버튼 small 객체 생성
    var cmDeleteSmall = document.createElement("small");
    //댓글 삭제 버튼 스타일 생성
    cmDeleteSmall.setAttribute("class", "float-right text-navy");
    //small 객체 내에 들어갈 a 객체 생성
    var cmDeleteA = document.createElement("a");
    //a객체에 class 속성 부여
    cmDeleteA.setAttribute("class", "cmDeleteBtn");
    //a객체에 댓글 번호로 id 설정
    cmDeleteA.setAttribute("id", cmNo[i]);
    //값 대입
    cmDeleteA.innerHTML = "X";
    //small객체에 a객체 append
    cmDeleteSmall.appendChild(cmDeleteA);

    //댓글 작성일 넣을 small객체 생성
    var cmDateSmall = document.createElement("small");
    //small객체 스타일 생성
    cmDateSmall.setAttribute("class", "float-right text-navy mr-3 cmDate");
    //small객체에 값 대입
    cmDateSmall.innerHTML = cmDate[i];

    //댓글 내용 넣을 span 태그 생성
    var cmContentSpan = document.createElement("span");
    //span 태그에 class 속성 부여
    cmContentSpan.setAttribute("class", "cmContnet");
    //값 대입
    cmContentSpan.innerHTML = cmContent[i] + "<br />";

    //댓글 작성자 넣을 small 태그 생성
    var cmWriterSmall = document.createElement("small");
    //small태그에 클래스 속성 부여 및 스타일 생성
    cmWriterSmall.setAttribute("class", "text-muted cmWriter");
    //값 대입 - 작성자 이름 + 아이디
    cmWriterSmall.innerHTML = cmWriter[i] + " " + cmWrtierId[i] + "<br />";

    //차례대로 각 부모에 append
    cmContentDiv.appendChild(cmDeleteSmall);
    cmContentDiv.appendChild(cmDateSmall);
    cmContentDiv.appendChild(cmContentSpan);
    cmContentDiv.appendChild(cmWriterSmall);

    cmBodyDiv.append(profileImg);
    cmBodyDiv.appendChild(cmContentDiv);

    cmCardBodyObj.appendChild(cmBodyDiv);

    //댓글 작성자 아이디와 현재 로그인한 아이디가 다를경우
    //관리자는 무조건 보이기
    if (currentLoginId == "admin") {
      //댓글 삭제 버튼 보이기
      cmDeleteSmall.removeAttribute("style");
    } else if (currentLoginId != cmWrtierId[i]) {
      //댓글 삭제 버튼 숨기기
      cmDeleteSmall.setAttribute("style", "display:none");
    }
    //댓글 삭제 버튼 보이기
    else cmDeleteSmall.removeAttribute("style");

    //댓글 삭제 버튼 클릭 시 이벤트 등록
    cmDeleteSmall.addEventListener("click", cmDeleteClickHandler);
  }

  //게시글 상세 페이지에 내용 대입
  function insertBdDatailElement() {
    //게시글 제목 대입
    detailTitleObj.innerHTML = bdDetailTitle;
    //게시글 작성자 대입
    infoWriterObj.innerHTML = bdDetailWriter;
    //게시글 작성일 대입
    infoDateObj.innerHTML = bdDetailDate;
    //게시글 상세 내용 대입
    detailContentObj.innerHTML = bdDetailContent;

    //로그인한 사원의 아이디와 게시글 작성자 아이디가 다를 경우
    //관리자는 무조건 수정삭제보이기
    if (
      loginInfoIdObj.innerHTML == "admin" ||
      loginInfoIdObj.innerHTML == bdDetailWriterId
    ) {
      bdBtnGroupObj.removeAttribute("style");
    } else if (loginInfoIdObj.innerHTML != bdDetailWriterId) {
      //수정,삭제 버튼 그룹 숨기기
      bdBtnGroupObj.setAttribute("style", "display:none");
    }

    //댓글 작성란의 작성자 현재 로그인한 사원 이름으로 대입
    cmWriterObj.innerHTML = currentLoginEmp;
  }

  //board-detail에서 사용할 backurl
  //게시글 상세 내용 조회 관련
  //var backurlBdDetail = "/back/showbddetail";
  var backurlBdDetail = "http://localhost:8888/gwback/board/";
  //게시글 내 댓글 조회 관련
  //var backurlCm = "/back/showboardcomment";
  var backurlCm = "http://localhost:8888/gwback/boardcomment/";
  //게시글 내 댓글 등록 관련
  //var backurlAddCm = "/back/addboardcomment";
  var backurlAddCm = "http://localhost:8888/gwback/boardcomment/addbdcm";
  //관리자 게시판 관련
  var adminbackurlBdDetail = "http://localhost:8888/gwback/admin/";
  var divContainer = document.querySelector(
    "body > div > div > main > div > div > div > div.card-header > div.card-body"
  );

  var downloadurl = "http://localhost:8888/gwback/board/download/";
  var admindownloaurl = "http://localhost:8888/gwback/admin/download/";
  var downloadUrl = currentLoginEmp == "admin" ? admindownloaurl : downloadurl;

  function createFile(fileName) {
    var divObj = document.createElement("div");
    var aObj = document.createElement("a");

    aObj.innerHTML = fileName.split("_")[1];
    aObj.setAttribute("href", downloadUrl + fileName);
    divObj.appendChild(aObj);
    divContainer.appendChild(divObj);
  }

  var detailloadUrl =
    currentLoginId == "admin"
      ? adminbackurlBdDetail + "/" + bdDetailBdNo
      : backurlBdDetail + "/" + bdDetailBdNo;

  //게시글 상세 내용 get
  //관리자인 경우와 일반 사용자의 경우
  // if (currentLoginEmp == "admin") {
  $.ajax({
    url: detailloadUrl,
    method: "get",
    success: function (responseData) {
      console.log(responseData.board);
      var board = responseData.board;
      bdDetailBdNo = board.bdNo;
      bdDetailTitle = board.bdTitle;
      bdDetailWriter = board.writer.name;
      bdDetailWriterId = board.writer.employeeId;
      bdDetailDate = board.bdDate;
      bdDetailContent = board.bdContent;

      //게시글 내용 대입하는 함수 호출
      insertBdDatailElement();
      console.log(responseData.fileName);
      if (responseData.fileName != null) {
        createFile(responseData.fileName);
      }
    },
  });

  //게시글 내 댓글 정보 get
  $.ajax({
    url: backurlCm + "/" + bdDetailBdNo,
    method: "get",
    data: {
      bdNo: bdDetailBdNo,
    },
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        cmNo[i] = e?.cmNo;
        cmContent[i] = e?.cmContent;
        cmWriter[i] = e?.cmWriter?.name;
        cmWrtierId[i] = e?.cmWriter?.employeeId;
        cmDate[i] = e?.cmDate;
      });

      //함수 호출
      if (cmNo[0] != undefined) {
        for (var i = 0; i < cmContent.length; i++) {
          createCmElement(i);
        }
      }
    },
  });

  //댓글 등록 클릭 핸들러
  function cmRegisterClickHandler(e) {
    var datajsObj = {};
    datajsObj.cmContent = cmTextAreaObj.value; //js객체의 프로퍼티 이름= 값대입
    datajsObj.cmWriter = {};
    datajsObj.cmWriter.employeeId = currentLoginId;
    if (currentLoginId == "admin") {
      datajsObj.bdAdmin = 0;
    } else {
      datajsObj.bdAdmin = 1;
    }

    var data = JSON.stringify(datajsObj); //js객체를 json문자열로 변환
    $.ajax({
      method: "POST",
      transformRequest: [null],
      transformResponse: [null],
      jsonpCallbackParam: "callback",
      url: backurlAddCm + "/" + bdDetailBdNo,
      headers: {
        Accept: "application/json, text/plain, */*",
        "Content-Type": "application/json;charset=utf-8",
      },
      data: data,
      timeout: {},
      success: function (responseData) {
        alert("댓글이 추가되었습니다!");
        //게시글 상세 페이지 재로딩
        if (currentLoginId == "admin") {
          var href = "notice-detail.html";
        } else {
          var href = "board-detail.html";
        }
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        // $(
        //   '#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li > a[href="board-detail.html"]'
        // ).trigger("click");
      },
    });
    e.preventDefault();
  }

  //댓글 작성란 form submit 이벤트 등록
  cmRegisterFormObj.addEventListener("submit", cmRegisterClickHandler);

  var $menuObj = $(
    "div.wrapper>nav.sidebar>div>div.simplebar-wrapper>div.simplebar-mask>div.simplebar-offset>div>div>ul>li"
  );
  //게시글 수정 버튼 객체
  var $modifyBtnObj = $(
    "div.wrapper>div.main>main.content div.row div.card-body button.modifyBtn"
  );
  //게시글 삭제 버튼 객체
  var $deleteBtnObj = $(
    "div.wrapper>div.main>main.content div.row div.card-body button.deleteBtn"
  );
  //댓글 삭제 버튼 객체
  var $cmDeleteBtnObj = $(
    "div.wrapper>div.main>main.content div.row div.card-body a.cmDeleteBtn"
  );

  //수정 버튼 클릭 시 클릭 이벤트 발생
  $modifyBtnObj.click(function () {
    //클릭된현재객체의 href속성값 얻기 : .attr('href');
    //현재 제목, 내용 로컬스토리지에 저장 - 수정 페이지에서 해당 내용 보여주기 위함
    localStorage.setItem("bdTitle", bdDetailTitle);
    localStorage.setItem("bdContent", bdDetailContent);
    // if (loginInfoIdObj.innerHTML == "admin") {
    var href = $(this).attr("href");
    switch (href) {
      case "notice-modify.html":
        //summernote api 등록
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          $("#summernote").summernote({
            height: 600, // 에디터 높이
            minHeight: null, // 최소 높이
            maxHeight: null, // 최대 높이
            focus: true, // 에디터 로딩후 포커스를 맞출지 여부
            lang: "ko-KR", // 한글 설정
            placeholder: "최대 2048자까지 쓸 수 있습니다", //placeholder 설정
          });
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    }
    switch (href) {
      case "board-modify.html":
        //summernote api 등록
        $content.load(href, function (responseTxt, statusTxt, xhr) {
          $("#summernote").summernote({
            height: 600, // 에디터 높이
            minHeight: null, // 최소 높이
            maxHeight: null, // 최대 높이
            focus: true, // 에디터 로딩후 포커스를 맞출지 여부
            lang: "ko-KR", // 한글 설정
            placeholder: "최대 2048자까지 쓸 수 있습니다", //placeholder 설정
          });
          if (statusTxt == "error")
            alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
        break;
    } // } else {
  });

  //게시글 제거 시 사용할 backurl
  //var backurlRemoveBd = "/back/removeboard";
  var backurlRemoveBd = "http://localhost:8888/gwback/board/removebd";
  var adminbackurlRemoveBd = "http://localhost:8888/gwback/admin/removebd";

  //게시글 삭제 버튼 클릭 시 이벤트 발생
  var deleteUrl =
    currentLoginId == "admin"
      ? adminbackurlRemoveBd + "/" + bdDetailBdNo
      : backurlRemoveBd + "/" + bdDetailBdNo;

  function deletetrigger() {
    if (currentLoginId == "admin") {
      $(
        "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li:nth-child(1) > a"
      ).trigger("click");
    } else {
      $(
        "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li:nth-child(4) > a"
      ).trigger("click");
    }
  }

  $deleteBtnObj.click(function (e) {
    // if (currentLoginId == "admin") {
    $.ajax({
      url: deleteUrl,
      method: "delete",
      transformRequest: [null],
      transformResponse: [null],
      jsonpCallbackParam: "callback",
      headers: {
        Accept: "application/json, text/plain, */*",
      },
      // data: JSON.stringify({
      //   removeTargetBdNo: bdDetailBdNo,
      //   removeWriterId: currentLoginId,
      // }),
      success: function (responseData) {
        alert("게시글이 삭제되었습니다!");
        deletetrigger();
      },
    });

    e.preventDefault();
  });
});
