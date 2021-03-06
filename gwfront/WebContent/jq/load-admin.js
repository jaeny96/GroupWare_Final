$(function () {
  var $mainProfileImg = $("img#mainProfileImg");

  $mainProfileImg.attr("src", "../img/avatars/avatar-2.jpg");

  //메뉴 이동 시 변경 될 부분
  var $content = $("div.wrapper>div.main>main.content");
  // 로그인 여부 확인
  var backurlCheckLogined = "http://localhost:8888/gwback/admin/chk-login";
  $.ajax({
    url: backurlCheckLogined,
    method: "get",
    success: function (responseData) {
      //로그인 되어있을 경우
      if (responseData.status == 1) {
        //게시판 관련 로컬 정보 미리 저장
        //현재 페이지, 현재 페이지 그룹 관련
        localStorage.setItem("bdCurrPage", 1);
        localStorage.setItem("bdCurrPageGroup", 1);

        //우측 상단 프로필 관련 객체
        var $dropDownProfileAObj = $("a.dropdownProfile");
        var $dropDownProfileItemObj = $("div.profileDropdown");
        $dropDownProfileItemObj.hide();
        //프로필 클릭 시 슬라이드 토글 이벤트 발생
        $dropDownProfileAObj.click(function (e) {
          $dropDownProfileItemObj.slideToggle(300);
        });

        //html내 메인 태그
        var mainObj = document.querySelector("div.wrapper>div.main");
        //프로필 내용 담겨있는 객체
        var mainProfileObj = mainObj.querySelector("div.profileDropdown");
        //로그인한 사원의 아이디가 들어갈 위치
        var mainLoginIdObj = mainProfileObj.querySelector("span.loginId");
        //로그인한 사원의 이름이 들어갈 위치
        var mainLoginNameObj = mainProfileObj.querySelector("span.loginName");

        var mainContentObj = mainObj.querySelector("main.content");
        //최근 게시글 관련 tbody 객체
        var mainBdTBodyObj = mainContentObj.querySelector(
          ".card-body tbody.mainBdTbody"
        );

        //로그인한 사원의 아이디값이 들어갈 변수
        var mainLoginId;
        //로그인한 사원의 이름값이 들어갈 변수
        var mainLoginName;

        //최근게시글 아이디값이 들어갈 arr
        var mainBdId = new Array();
        //최근게시글 제목값이 들어갈 arr
        var mainBdTitle = new Array();
        //최근게시글 작성자값이 들어갈 arr
        var mainBdWriter = new Array();
        //최근게시글 날짜값이 들어갈 arr
        var mainBdDate = new Array();

        //ajax요청 시 사용할 backurl 선언

        //ajax요청 시 사용할 backurl 선언
        //프로필 정보
        var backurlProfile = "http://localhost:8888/gwback/admin/profile";

        //최근게시글-> 이거는 com.group.~~에서 불러와야됨
        var backurlBd = "http://localhost:8888/gwback/admin/board";
        //로그아웃
        var backurlLogout = "http://localhost:8888/gwback/admin/logout";
        //로그아웃 버튼 객체
        var logoutBtnObj = document.querySelector("a.logoutBtn");

        //프로필 정보를 채우는 역할의 함수
        function insertProfileInfo() {
          mainLoginIdObj.innerHTML = mainLoginId;
          mainLoginNameObj.innerHTML = mainLoginName;
        }

        //프로필 정보 get
        $.ajax({
          url: backurlProfile,
          method: "get",
          "Content-Type": "application/json",
          success: function (responseData) {
            mainLoginId = responseData.employeeId;
            mainLoginName = responseData.name;
            //함수 호출
            insertProfileInfo();
          },
        });

        //최근게시글 관련 정보 및 tr/td 객체들을 생성해주는 역할의 함수
        function createMainBdElement(i) {
          //tr객체 생성
          var tr = document.createElement("tr");
          //결재예정문서 제목값이 들어갈 td객체 생성
          var tdBdTitle = document.createElement("td");
          //제목 클릭 시 해당 상세 페이지로 넘어가야하므로 a 객체 생성
          var tdBdTitleA = document.createElement("a");
          //값 대입
          tdBdTitleA.innerHTML = mainBdTitle[i];
          //a태그에 클래스 속성 부여함으로 스타일 적용
          tdBdTitleA.setAttribute("class", "sidebar-link-js");
          //a태그에 id 속성 부여
          tdBdTitleA.setAttribute("id", mainBdId[i]);
          //a태그 이동 페이지 설정
          tdBdTitleA.setAttribute("href", "notice-detail.html");
          //a태그 상세 스타일 적용
          tdBdTitleA.setAttribute("style", "color:black");
          tdBdTitle.setAttribute("style", "width:60%");
          //생성해놓은 제목td객체에 append
          tdBdTitle.appendChild(tdBdTitleA);

          //결재예정문서 날짜값 들어갈 td객체 생성
          var tdBdDate = document.createElement("td");
          //값 대입
          tdBdDate.innerHTML = mainBdDate[i];

          //tr 객체에 순서대로 append
          tr.appendChild(tdBdTitle);
          tr.appendChild(tdBdDate);

          //결재예정문서 tbody객체에 해당 tr append
          mainBdTBodyObj.appendChild(tr);
        }

        //최근게시글 정보 get
        $.ajax({
          url: backurlBd,
          method: "get",
          success: function (responseData) {
            $(responseData).each(function (i, e) {
              mainBdId[i] = e.bdNo;
              mainBdTitle[i] = e.bdTitle;
              mainBdWriter[i] = e.writer.employeeId;
              mainBdDate[i] = e.bdDate;
            });

            //함수 호출
            for (var i = 0; i < mainBdId.length; i++) {
              createMainBdElement(i);
            }

            //최근게시글 제목 객체
            $mainBdTitleObj = $(
              'body > div > div > main > div > div:nth-child(1) > div.col-12.col-md-6.d-flex.order-2.order-xxl-3 > div > div.card-body.d-flex > div > table > tbody > tr > td >a[href="notice-detail.html"]'
            );

            //최근게시글 제목 객체 클릭 시 이벤트 발생
            $mainBdTitleObj.click(function (e) {
              e.preventDefault();
              //클릭시 a링크에 담겨있는 문서값 저장
              localStorage.setItem("bdNumber", e.target.id);
              var href = $(this).attr("href");

              switch (href) {
                case "notice-detail.html":
                  //컨텐트에 로드
                  $content.load(href, function (responseTxt, statusTxt, xhr) {
                    if (statusTxt == "error")
                      alert("Error: " + xhr.status + ": " + xhr.statusText);
                  });
                  break;
              }
              return false;
            });
          },
        });

        //로그아웃 관련 클릭 이벤트 핸들러
        function logoutBtnClickHandler() {
          $.ajax({
            url: backurlLogout,
            method: "get",
            success: function (responseData) {
              //로그인 페이지로 이동
              location.href = "http://localhost:8888/gwfront/admin";
            },
          });
        }
        //로그아웃 버튼 클릭 이벤트 등록
        logoutBtnObj.addEventListener("click", logoutBtnClickHandler);

        //-----메뉴 로드 시작-----
        //메뉴 이동 시 변경 될 부분
        var $content = $("html>body>div.wrapper>div.main>main.content");

        //sidebar menu Obj 찾기
        var $menuObj = $(
          "#sidebar > div > div.simplebar-wrapper > div.simplebar-mask > div > div > div > ul > li > a"
        );

        //메뉴 객체 클릭 이벤트 발생 시
        $menuObj.click(function (e) {
          //sidebar-item 활성화 모두 풀기(없애기)
          $menuObj.closest("li").attr("class", "sidebar-item mb-2");
          //클릭된현재객체의 href속성값 얻기 : .attr('href');
          var href = $(this).attr("href");

          switch (href) {
            case "notice.html":
            case "employee.html":
            case "job-manage.html":
              var realAdminPath = "/gwfront/admin/" + href;
              //클릭한 객체의 sidebar-item만 활성화 시키기
              $(this).closest("li").attr("class", "sidebar-item mb-2 active");
              $content.load(
                realAdminPath,
                function (responseTxt, statusTxt, xhr) {
                  if (statusTxt == "error") {
                    alert("Error: " + xhr.status + ": " + xhr.statusText);
                  }
                }
              );
              break;
          }
          return false;
        });
      } else {
        console.log(responseData.status);
        alert("접근할 수 없는 세션 정보입니다.");
        location.href = "http://localhost:8888/gwfront/admin";
      }
    },
    error: function (request, status, error) {
      alert("접근하기 위해 로그인이 필요합니다.");
      location.href = "http://localhost:8888/gwfront/admin";
    },
  });
});
