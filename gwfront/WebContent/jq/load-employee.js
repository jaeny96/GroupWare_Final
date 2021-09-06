$(function () {
  //------------admin 설정-----------------//
  var loginInfoIdObj = document.querySelector(
    "div.profileDropdown span.loginId"
  );
  console.log(loginInfoIdObj);

  //수정 모달로 넘어가는 버튼
  let modifyEmpBtn = $("#modifyEmpBtn");
  //직원 추가 버튼
  let addEmpBtn = $("#addEmp");
  //로그인한 사람이 admin일 경우 수정 버튼 보이게

  if (loginInfoIdObj.innerHTML == "admin") {
    modifyEmpBtn.removeClass("hidden");
    addEmpBtn.removeClass("hidden");
  }
  //-------------admin 설정-------------//

  //부서 메뉴 감싸주는 card Div 객체
  var $cardObj = $("div#navMenu");
  //옆에 메뉴 객체
  var navObj = document.querySelector("li.navObj");
  //메뉴 객체의 부서 목록 열어주는 a 태그
  var navAObj = navObj.querySelector("a");
  //메뉴의 드롭다운 객체
  var ulDeptObj = navObj.querySelector("ul.sidebar-dropdown");
  //card Div객체의 현재 높이 값
  var cardObjHeight = $cardObj.height();
  //부서 기본 값 li 높이 * 부서 수
  var deptMenuHeight = 40 * 8;

  //클릭이벤트 시 card Div 객체의 높이 값이 달라짐
  navAObj.addEventListener("click", function () {
    if (navAObj.getAttribute("aria-expanded") == false) {
      $cardObj.animate({ height: cardObjHeight + "px" }, 400);
    } else {
      $cardObj.animate({ height: cardObjHeight + deptMenuHeight + "px" }, 400);
      deptMenuHeight = ulDeptObj.offsetHeight;
    }
  });

  // var $ulDeptObj = $(
  //   "body > div > div > main > div.container-fluid.p-0 > div > div.col-md-4.col-xl-3 > div > div > ul > li > a"
  // );
  // var $dropdownItem = $("#forms");
  // $ulDeptObj.click(function () {
  //   $dropdownItem.slideToggle(300);
  // });

  //내용 담는 객체
  var contentObj = document.querySelector("div.deptContent");
  //내용담는 객체의 헤더 객체
  var empHeaderObj = contentObj.querySelector("h5.empHeader");
  //내용담는 객체의 내용
  var empBodyObj = contentObj.querySelector("div.empBody");
  //내용 담는 객체의 각 내용들을 감싸는 div arr
  var empContentObj = new Array();
  //사원 상세 정보 모달 창
  var modalDetailObj = document.querySelector("div#modalDetail");
  //모달 창 내용
  //사원 이름 객체
  var modalNameObj = modalDetailObj.querySelector("h4");
  //사원 직급 객체
  var modalPositionObj = modalDetailObj.querySelector(
    "div.cardBody div:first-child>div"
  );
  //사원 사번 객체
  var modalEmployeeIdObj = modalDetailObj.querySelector(
    "div.cardBody div:nth-child(2)>div"
  );
  //사원 부서 객체
  var modalDepartmentObj = modalDetailObj.querySelector(
    "div.cardBody div:nth-child(3)>div"
  );
  //사원 직무 객체
  var modalJobObj = modalDetailObj.querySelector(
    "div.cardBody div:nth-child(4)>div"
  );
  //사원 핸드폰번호 객체
  var modalPhoneObj = modalDetailObj.querySelector(
    "div.cardBody div:nth-child(5)>div"
  );
  //사원 이메일 객체
  var modalEmailObj = modalDetailObj.querySelector(
    "div.cardBody div:last-child>div"
  );

  //검색 form 객체
  var formObj = document.querySelector("form.searchEmp");
  //검색 단어 input 객체
  var wordObj = formObj.querySelector("input.searchWord");

  //불러온 부서명 arr
  var deptArr = new Array();
  //불러온 부서별 사원 수 arr
  var deptCntArr = new Array();
  //불러온 부서번호 arr
  var deptIdArr = new Array();
  //불러온 부서별 사원 이름명 arr
  var empArr = new Array();
  //불러온 부서별 사원 직책명 arr
  var positionArr = new Array();
  //불러온 사원의 사번 arr
  var empIdArr = new Array();
  //수정 모달창 내 사원의 직무 배열
  var empJobArr = new Array();
  //수정 모달창 내 부서 배열
  var departmentArr = new Array();
  //수정 모달창 내 부서아이디 배열
  var departmentIdArr = new Array();

  //사원 상세 사원명 변수
  var detailName;
  //사원 상세 직급 변수
  var detailPosition;
  //사원 상세 사번 변수
  var detailEmployeeId;
  //사원 상세 부서 변수
  var detailDepartment;
  //사원 상세 직무 변수
  var detailJob;
  //사원 상세 핸드폰 번호 변수
  var detailPhone;
  //사원 상세 이메일 변수
  var detailEmail;

  //employee에서 사용할 backurl
  //부서불러오기
  var backurlDept = "http://localhost:8888/gwback/employee/dep";
  //전체 사원 불러오기
  var backurlAllEmp = "http://localhost:8888/gwback/employee/all";
  //부서별 사원 불러오기 +{dep}
  var backurlDeptEmp = "http://localhost:8888/gwback/employee/byDept/";
  //사원 상세 불러오기 +{id}
  var backurlEmpDetail = "http://localhost:8888/gwback/employee/";
  //사원명으로 검색한 결과 불러오기 +{word}
  var backurlSearchEmp = "http://localhost:8888/gwback/employee/search/";
  //사원 수정
  var backurlUpdateEmp = "http://localhost:8888/gwback/admin/employee/update";
  var backurlShowPosition =
    "http://localhost:8888/gwback/admin/employee/position";

  var backurlShowJob = "http://localhost:8888/gwback/admin/employee/job";

  var backurlShowDep = "http://localhost:8888/gwback/admin/employee/department";
  var backurlAddEmp = "http://localhost:8888/gwback/admin/employee/add";

  //사원 클릭 시 클릭 이벤트 핸들러
  function empClickHandler(e) {
    // console.log(e.target.id);

    var empInfoArr = e.target.id.split("/");
    //  console.log(empInfoArr[0] + empInfoArr[1]);

    $.ajax({
      url: backurlEmpDetail + empInfoArr[0],
      method: "get",
      data: {
        empId: empInfoArr[0],
        empName: empInfoArr[1],
      },
      success: function (responseData) {
        // console.log(responseData);
        detailName = responseData.name;
        detailPosition = responseData.position.positionTitle;
        detailEmployeeId = responseData.employeeId;
        detailDepartment = responseData.department.departmentTitle;
        detailJob = responseData.job.jobTitle;
        detailPhone = responseData.phoneNumber;
        detailEmail = responseData.email;
        detailEnabled = responseData.enabled;

        console.log(detailEnabled);
        openTargetModal("." + empInfoArr[0] + "openDetail", "modalDetail");

        //-----------------------수정 시작--------------//
        if (loginInfoIdObj.innerHTML == "admin") {
          //수정 시, 기존 값이 select option 값에 삽입되도록 한다.
          //position의 옵션 배열
          var positionOpt = document.querySelectorAll("#positionSelect option");
          //job의 옵션 배열
          var jobOpt = document.querySelectorAll("#jobSelect option");
          //department의 옵션 배열
          var departmentOpt = document.querySelectorAll(
            "#departmentSelect option"
          );

          //기존 position을 select option에 배정한다
          for (var i = 0; i < positionOpt.length; i++) {
            if (positionOpt[i].value == responseData.position.positionId) {
              $("#positionSelect option:eq(" + i + ")").attr(
                "selected",
                "selected"
              );
              break;
            }
          }
          //기존 job을 select option에 배정한다
          for (var i = 0; i < jobOpt.length; i++) {
            if (jobOpt[i].value == responseData.job.jobTitle) {
              $("#jobSelect option:eq(" + i + ")").attr("selected", "selected");
              break;
            }
          }

          //기존 department를 select option에 배정한다
          for (var i = 0; i < departmentOpt.length; i++) {
            if (
              departmentOpt[i].value == responseData.department.departmentId
            ) {
              $("#departmentSelect option:eq(" + i + ")").attr(
                "selected",
                "selected"
              );
              break;
            }
          }
        }
        //활성화/ 비활성화
        // for (var i = 0; i < updateStatus.length; i++) {
        //   if (
        //     updateStatus[i].value == responseData.enabled
        //   ) {
        //     $("#statusSelect option:eq(" + i + ")").attr(
        //       "selected",
        //       "selected"
        //     );
        //     break;
        //   }
        // }

        //-----------------------수정 끝--------------//
      },
    });
    //사원 상세 정보 모달 창 열기
  }

  //타겟 모달 열기
  function openTargetModal(modalId, modalObj) {
    console.log("opend", detailName);
    //모달 이름 객체에 사원명 대입
    modalNameObj.innerHTML = detailName;
    //모달 직급 객체에 사원의 직급 대입
    modalPositionObj.innerHTML = detailPosition;
    //모달 사번 객체에 사원의 사번 대입
    modalEmployeeIdObj.innerHTML = detailEmployeeId;
    //모달 부서 객체에 사원의 부서 대입
    modalDepartmentObj.innerHTML = detailDepartment;
    //모달 직무 객체에 사원의 직무 대입
    modalJobObj.innerHTML = detailJob;
    //모달 핸드폰번호 객체에 사원의 핸드폰 번호 대입
    modalPhoneObj.innerHTML = detailPhone;
    //모달 이메일 객체에 사원의 이메일 대입
    modalEmailObj.innerHTML = detailEmail;

    //모달창 열기 버튼
    var openBtn = document.querySelector(modalId);
    //모달 객체
    var modal = document.querySelector("#" + modalObj + ">.modal");
    //모달의 뒷 배경
    var overlay = modal.querySelector(".modal_overlay");
    //모달 닫기 버튼
    var xBoxBtn = modal.querySelector("button.xBox");

    modal.classList.remove("hidden");
    var closeModal = () => {
      modal.classList.add("hidden");
    };
    if (openBtn != null) {
      //input에는 없는 overlay 클릭 -> close 기능
      overlay.addEventListener("click", closeModal);
      //모달창 닫기 버튼
      xBoxBtn.addEventListener("click", closeModal);
    }
  }
  //---------------------수정 ----------------------//

  //상세보기 모달
  let modal = document.querySelector("#modalDetail>.modal");
  if (loginInfoIdObj.innerHTML == "admin") {
    //수정 모달
    let modifyModal = document.querySelector("#modalEmpModify>.modal");
    let modifyModalName = modifyModal.querySelector("h4");
    let updateEmployeeId = $("#modifyEmpId");
    let updatePhoneNum = $("#modifyEmpPhoneNum");
    let updateEmail = $("#modifyEmpEmail");

    //수정 셀렉트
    let positionSelect = $("#positionSelect");
    let jobSelect = $("#jobSelect");
    let departmentSelect = $("#departmentSelect");

    //추가 셀릭트
    let addPositionSelect = $("#addPositionSelect");
    let addDepartmentSelect = $("#addDepartmentSelect");
    let addJobSelect = $("#addJobSelect");

    //추가 input
    let addEmpName = $("#addEmpName");
    let addEmpId = $("#addEmpId");
    let addEmpPhoneNum = $("#addEmpPhoneNum");
    let addEmpEmail = $("#addEmpEmail");
    let addEmpPassword = $("#addEmpPassword");
    let addEmpHireDate = $("#addEmpHireDate");

    //수정 버튼 클릭
    modifyEmpBtn.click(function () {
      //상세보기 모달 닫기
      modal.classList.add("hidden");
      //수정창 모달 열기
      modifyModal.classList.remove("hidden");

      //수정 대상 임직원 이름
      modifyModalName.innerHTML = detailName;
      //input에 기존 값 넣기
      updateEmployeeId.attr("value", detailEmployeeId);
      updatePhoneNum.attr("value", detailPhone);
      updateEmail.attr("value", detailEmail);

      //모달의 뒷 배경
      let overlay = modifyModal.querySelector(".modal_overlay");
      //모달 닫기 X 버튼
      let xBoxBtn = modifyModal.querySelector("button.xBox");
      //모달 닫기 버튼
      let closeBtn = modifyModal.querySelector("button.closeBtn");

      let closeModal = () => {
        modifyModal.classList.add("hidden");
      };

      overlay.addEventListener("click", closeModal);
      //모달창 닫기 버튼
      xBoxBtn.addEventListener("click", closeModal);

      closeBtn.addEventListener("click", closeModal);
    });

    //수정 확인 버튼
    let modifySubmitBtn = document.querySelector("#modifySubBtn");

    //수정 확인 버튼 클릭이벤트 : 수정데이터 전송
    modifySubmitBtn.addEventListener("click", function () {
      if(updateEmployeeId.val()!=="" && updatePhoneNum.val()!==""&& updateEmail.val()!=="") {
      $.ajax({
        url: backurlUpdateEmp,
        method: "put",
        headers: {
          Accept: "application/json, text/plain, */*",
          "Content-Type": "application/json;charset=utf-8",
        },
        data: JSON.stringify({
          employeeId: detailEmployeeId,
          department: {
            departmentId: $("#departmentSelect option:selected").val(),
          },
          job: { jobId: $("#jobSelect option:selected").val() },
          position: { positionId: $("#positionSelect option:selected").val() },
          phoneNumber: updatePhoneNum.val(),
          email: updateEmail.val(),
          enabled: $("#statusSelect option:selected").val(),
        }),
        success: function () {
          alert("수정이 완료되었습니다.");
          //alert($("#statusSelect option:selected").val());

          var $content = $("div.wrapper>div.main>main.content");
          var href = "/gwfront/admin/employee.html";
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
    }else{
      alert('빈칸을 모두 입력해주세요.');
    }
    });
//-----------------------수정 끝--------------//

 //---------------직무, 직책, 부서 불러오기  시작----//

    //직책 position select option을 불러오기
    let positionIdArr= new Array();
    $.ajax({
      url: backurlShowPosition,
      method: "get",
      success: function (responseData) {
        console.log(responseData);
        $(responseData).each(function (i, e) {
          positionIdArr[i] = e.positionId;
          positionArr[i] = e.positionTitle;
          //수정 모달 셀렉트
          positionSelect.append(
            '<option value="' +  positionIdArr[i] + '">' +  positionArr[i] + "</option>"
          );
   
          //추가 모달 셀렉트
          addPositionSelect.append(
            '<option value="' +  positionIdArr[i]+ '">' + positionArr[i] + "</option>"
          );
          //console.log( positionIdArr[i]);
         // console.log( positionArr[i] );
        });
      },
    });

    //직무 job select동적 처리
    $.ajax({
      method: "GET",
      transformRequest: [null],
      transformResponse: [null],
      jsonpCallbackParam: "callback",
      url: backurlShowJob,
      headers: {
        Accept: "application/json, text/plain, */*",
      },
      data: "",
      timeout: {},
      success: function (responseData) {
        $(responseData).each(function (i, e) {
          empJobArr[i] = e;
          //수정 모달 셀렉트
          jobSelect.append(
            '<option value="' + empJobArr[i] + '">' + empJobArr[i] + "</option>"
          );
         
          //추가 모달 셀렉트
          addJobSelect.append(
            '<option value="' + empJobArr[i] + '">' + empJobArr[i] + "</option>"
          );
        });
      },
    });

    //부서 select  동적 처리
    $.ajax({
      url: backurlShowDep,
      method: "get",
      success: function (responseData) {
        $(responseData).each(function (i, e) {
          departmentIdArr[i] = e.departmentId;
          departmentArr[i] = e.departmentTitle;

          //수정 모달 셀렉트
          departmentSelect.append(
            '<option value="' +
              departmentIdArr[i] +
              '">' +
              departmentArr[i] +
              "</option>"
          );
          //추가 모달 셀렉트
          addDepartmentSelect.append(
            '<option value="' +
              departmentIdArr[i] +
              '">' +
              departmentArr[i] +
              "</option>"
          );
        });
      },
    });

//---------------직무, 직책, 부서 불러오기 끝----//

    //---------------------직원 추가--------------//

    //직원 추가 모달
    let addEmpModal = document.querySelector("#addEmpModal > div.modal");
    //직원 추가 모달 내 닫기 버튼
    let xBoxBtn = addEmpModal.querySelector("button.xBox");
    let colseBtn = addEmpModal.querySelector("button.closeBtn");
    //직원 추가 모달 여는 버튼

    addEmpBtn.click(function () {
      //모달 열기
      addEmpModal.classList.remove("hidden");
      //모달 닫기 함수
      var closeModal = () => {
        addEmpModal.classList.add("hidden");
      };
      //모달창 닫기 버튼 클릭 이벤트
      xBoxBtn.addEventListener("click", closeModal);
      colseBtn.addEventListener("click", closeModal);
    });

    //직원 추가하기 submit 버튼
    let addEmpSubmitBtn = $("#addSubBtn");
    let addEmpForm = $("#addEmpForm");
    //직원 추가하기 submit 버튼 클릭이벤트
    addEmpSubmitBtn.click(function () {
      //required로 문제를 해결하려고 했지만, 이벤트가 꼬여서 자꾸 메인으로 넘어가는 문제가 있어 다음과 같이 해결함. 
      if(addEmpId.val()!=="" && addEmpPhoneNum.val()!==""&& addEmpEmail.val()!==""&&addEmpHireDate.val()!==""&&addEmpPassword.val()!=="") {
      $.ajax({
        url: backurlAddEmp,
        method: "post",
        headers: {
          Accept: "application/json, text/plain, */*",
          "Content-Type": "application/json;charset=utf-8",
        },
        data: JSON.stringify({
          employeeId: addEmpId.val(),
          name: addEmpName.val(),
          department: {
            departmentId: $("#addDepartmentSelect option:selected").val(),
          },
          job: { jobId: $("#addJobSelect option:selected").val() },
          position: {
            positionId: $("#addPositionSelect option:selected").val(),
          },
          phoneNumber: addEmpPhoneNum.val(),
          email: addEmpEmail.val(),
          hireDate: addEmpHireDate.val(),
          enabled: $("#addStatusSelect option:selected").val(),
          password: addEmpPassword.val(),
        }),

        success: function () {
          //alert($("#addPositionSelect option:selected").val());
          alert("직원이 추가되었습니다.");
          var $content = $("div.wrapper>div.main>main.content");
          var href = "/gwfront/admin/employee.html";
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
    }else{
      alert('빈칸을 전부 입력해주세요.');
    }
    });
  }
  //---------------------직원 추가 끝--------------//
  //해당 객체 제거
  function removeEmpElement(target) {
    target.remove();
  }

  //cardBody 객체 생성
  function createCardBody() {
    empBodyObj = document.createElement("div");
    empBodyObj.setAttribute("class", "card-body h-100 ");
    contentObj.appendChild(empBodyObj);
  }

  //부서별 사원 내용 생성
  function createEmpElement(i) {
    //사용자 아이콘 div 객체 생성
    var divIcon = document.createElement("div");
    //div객체에 class속성 부여
    divIcon.setAttribute("class", "fa fa-user fa-3x mr-3");

    //사원 정보 내용 div 객체 생성
    var divContent = document.createElement("div");
    //div 객체에 class 속성 부여
    divContent.setAttribute(
      "class",
      "flex-grow-1 mr-2 " + empIdArr[i] + "openDetail"
    );
    divContent.setAttribute("style", "cursor:pointer;");
    //div에 id 속성 부여
    divContent.setAttribute("id", empIdArr[i] + "/" + empArr[i]);

    //직급 담겨있는 samll 객체 생성
    var small = document.createElement("small");
    //small객체 스타일 적용
    small.setAttribute("class", "text-muted");
    //값 대입
    small.innerHTML = positionArr[i];
    //small 객체에 id 속성 부여
    small.setAttribute("id", empIdArr[i] + "/" + empArr[i]);

    //사원정보 내용 div 객체에 값 대입
    divContent.innerHTML = empArr[i] + "<br/>";

    //차례대로 각 부모에 append
    divContent.appendChild(small);
    //사원 정보 div 클릭 이벤트 등록
    divContent.addEventListener("click", empClickHandler);

    empContentObj.appendChild(divIcon);
    empContentObj.appendChild(divContent);
  }

  //내용 감싸주는 div 만들기
  function createEmpElementBig() {
    empContentObj = document.createElement("div");
    empContentObj.setAttribute("class", "d-flex align-items-start mb-4");
    empBodyObj.appendChild(empContentObj);
  }

  //생성한 배열 초기화
  function emptyElement() {
    removeEmpElement(empBodyObj);
    empArr = [];
    positionArr = [];
    empIdArr = [];
  }

  //dept에 맞는 사원 조회
  function selectEmpElement(dept) {
    //배열 초기화
    emptyElement();
    $.ajax({
      url: backurlDeptEmp + dept,
      method: "get",
      data: {
        deptId: dept,
      },
      success: function (responseData) {
        $(responseData).each(function (i, e) {
          empArr[i] = e.name;
          positionArr[i] = e.position.positionTitle;
          empIdArr[i] = e.employeeId;
        });
        //cardBody 생성
        createCardBody();

        //한줄에는 3개씩
        for (var i = 0; i < empArr.length; i++) {
          if (i % 3 == 0) {
            //3개의 emp카드 감싸는 객체 생성
            createEmpElementBig();
          }
          //emp요소 생성 함수 호출
          createEmpElement(i);
        }
      },
    });
  }

  //부서명 클릭 handler
  function deptClickHandler(e) {
    empHeaderObj.innerText = e.target.innerHTML;
    console.log(e.target.id);
    var depInfoArr = e.target.id.split("/");
    selectEmpElement(e.target.id);
  }

  //부서 내비게이션 생성 함수
  function createDeptElement(i) {
    //li 객체 생성
    var li = document.createElement("li");
    //li객체에 스타일 적용
    li.setAttribute("class", "sidebar-item");

    //해당 내비게이션 클릭 시 부서에 해당하는 사원들 보여줘야 하므로 a 객체 생성
    var a = document.createElement("a");
    //a객체에 스타일 적용
    a.setAttribute("class", "sidebar-link-js");
    //a객체에 아이디 속성 dept아이디로 부여
    a.setAttribute("id", deptIdArr[i]);
    //값 대입
    a.innerHTML = deptArr[i] + "(" + deptCntArr[i] + ")";
    //a객체에 클릭 이벤트 등록
    a.addEventListener("click", deptClickHandler);

    //차례대로 각 부모에 append
    li.appendChild(a);
    ulDeptObj.appendChild(li);
  }

  //부서 + 부서별 사원수 get
  $.ajax({
    url: backurlDept,
    method: "get",
    success: function (responseData) {
      //console.log(responseData);
      $(responseData).each(function (i, e) {
        deptArr[i] = e.departmentTitle;
        deptCntArr[i] = e.count;
        deptIdArr[i] = e.departmentId;
        //console.log(deptArr[i]);
      });
      //부서 li생성하는 함수 호출
      for (var i = 0; i < deptArr.length; i++) {
        createDeptElement(i);
      }
    },
  });

  //전체 사원 정보 get
  $.ajax({
    url: backurlAllEmp,
    method: "get",
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        //console.log(responseData);
        empArr[i] = e.name;
        positionArr[i] = e.position.positionTitle;
        empIdArr[i] = e.employeeId;
      });
      for (var i = 0; i < empArr.length; i++) {
        //사원 3명씩 감싸는 객체 생성
        if (i % 3 == 0) {
          createEmpElementBig();
        }
        //사원 객체 생성
        createEmpElement(i);
      }
    },
  });

  //검색 submit 이벤트 핸들러
  function searchSubmitHandler(e) {
    //검색어가 비어있을 때

    //배열 초기화
    emptyElement();
    //'검색한 단어'의 검색 결과
    empHeaderObj.innerText = "'" + wordObj.value + "'의 검색 결과";
    $.ajax({
      url: backurlSearchEmp + wordObj.value,
      method: "post",
      data: {
        word: wordObj.value,
      },
      success: function (responseData) {
        $(responseData).each(function (i, e) {
          empArr[i] = e.name;
          positionArr[i] = e.position.positionTitle;
          empIdArr[i] = e.employeeId;
        });
        //cardBody 생성
        createCardBody();

        for (var i = 0; i < empArr.length; i++) {
          //사원 3명씩 감싸는 객체 생성
          if (i % 3 == 0) {
            createEmpElementBig();
          }
          //사원 객체 생성
          createEmpElement(i);
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

  //검색  form 객체에 submit 이벤트 등록
  //만약 검색어가 없으면 false 반환

  var pattern = /\s/g; // 공백 체크 정규표현식
  $("button.empSearchBtn").click(function () {
    if (wordObj.value === "" || wordObj.value.match(pattern)) {
      alert("검색어를 입력하세요");
      return false;
    } else {
      formObj.addEventListener("submit", searchSubmitHandler);
    }
  });
});
