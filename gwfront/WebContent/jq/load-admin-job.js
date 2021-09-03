//만약, 변경된 사람이 존재할때 저장하는 변수
var finalNewEmpIdNJobArr = new Array(); //새로 변경될 값
var divName; //기존 값
var isChange = false;

$(document).ready(function () {
  //변경 데이터가 있으면, 페이지 검사 실행하기  실행하기
  // confirmAlert = confirm(
  //   "삭제하면 데이터는 복구 불가능합니다. \n정말 삭제하시겠습니까?"
  // );

  // var href = $(this).attr("href");
  // switch (href) {
  //   case "job-manage.html":
  //   case "notice-manage.html":
  //   case "emp-manage.html":
  //     if (isChange) {
  //       if (!confirmAlert) {
  //         $(
  //           "html>body>div.wrapper>#sidebar > div.js-simplebar > div.simplebar-wrapper > div.simplebar-mask > div.simplebar-offset > div > div > ul > li.sidebar-item > a.sidebar-link[href='/gwfront/admin/emp-manage.html']"
  //         ).trigger("click");
  //       } else {
  //         $(
  //           "html>body>div.wrapper>#sidebar > div.js-simplebar > div.simplebar-wrapper > div.simplebar-mask > div.simplebar-offset > div > div > ul > li.sidebar-item > a.sidebar-link[href='/gwfront/admin/job-manage.html']"
  //         ).trigger("click");
  //       }
  //     } else {
  //       $(
  //         "html>body>div.wrapper>#sidebar > div.js-simplebar > div.simplebar-wrapper > div.simplebar-mask > div.simplebar-offset > div > div > ul > li.sidebar-item > a.sidebar-link[href='/gwfront/admin/emp-manage.html']"
  //       ).trigger("click");
  //     }
  //     break;
  // }

  //저장버튼 클릭시(ajax발동)
  $(document).on("click", "#saveJob", function () {
    isChange = false;
    let tableArray = insertJobTable();
    //변경 데이터가 있으면 실행하기
    //finalNewEmpIdNJobArr=newEmpIdNJobArr;
    console.log("최종 제출" + JSON.stringify(finalNewEmpIdNJobArr));

    if (finalNewEmpIdNJobArr != null) {
      console.log("업데이트!");
      $.ajax({
        method: "PUT",
        transformRequest: [null],
        transformResponse: [null],
        jsonpCallbackParam: "callback",
        url: "/gwback/admin/job/changeJob/" + divName,
        headers: {
          Accept: "application/json, text/plain, */*",
          "Content-Type": "application/json;charset=utf-8",
        },
        data: JSON.stringify(finalNewEmpIdNJobArr),
        timeout: {},
        success: function () {
          //페이지 리로드
          var $content = $("div.wrapper>div.main>main.content");
          var href = "/gwfront/admin/job-manage.html";
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
    //기본으로 실행하기
    $.ajax({
      method: "PUT",
      transformRequest: [null],
      transformResponse: [null],
      jsonpCallbackParam: "callback",
      url: "/gwback/admin/job/save",
      headers: {
        Accept: "application/json, text/plain, */*",
        "Content-Type": "application/json;charset=utf-8",
      },
      data: JSON.stringify(tableArray),
      timeout: {},
      success: function () {
        alert("저장 완료 되었습니다!");
        var $content = $("div.wrapper>div.main>main.content");
        var href = "/gwfront/admin/job-manage.html";
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
  });

  //텍스트 최초 클릭시
  $(document).on("click", ".editable", function () {
    var value = $(this).text();
    console.log(value);
    var input =
      "<span class='span-click'>" +
      value +
      "</span><span class='fa fa-eraser'></span>";
    $(this).html(input);

    $(this).removeClass("editable");
    return false;
  });
  //텍스트 다시 클릭시
  $(document).on("click", ".span-click", function () {
    var text = $(this).text();
    var td = $(this).parent("div");
    console.log(td);
    td.html(text);
    td.addClass("editable");
  });

  //최초 테이블 생성
  console.log("실행");
  var $jobListObj = $("#jobListObj");
  var jobListHtml = "";

  $.ajax({
    method: "GET",
    transformRequest: [null],
    transformResponse: [null],
    jsonpCallbackParam: "callback",
    url: "/gwback/admin/job/all",
    headers: {
      Accept: "application/json, text/plain, */*",
    },
    data: "",
    timeout: {},
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        jobListHtml +=
          '<div id="' +
          e.jobId +
          '" class="editable" style="padding:30px; display:inline-block;border:1px solid;">' +
          e.jobTitle +
          "</div>";
      });
      $jobListObj.html(jobListHtml);
      //insertJobTable(); //테이블 미리채우기
    },
  });

  //지우개 클릭시
  $(document).on("click", ".fa-eraser", function () {
    var div = $(this).parent("div");

    $.ajax({
      method: "GET",
      transformRequest: [null],
      transformResponse: [null],
      jsonpCallbackParam: "callback",
      url: "/gwback/admin/job/emp-all/" + div.text().trim(),
      headers: {
        Accept: "application/json, text/plain, */*",
      },
      data: "",
      timeout: {},
      success: function (responseObj) {
        if (responseObj.length == 0) {
          isChange = true; //변경 확인
          //직무에 해당 사원이 없는 경우
          console.log("사원 없음");
          hasNotPersonModal(div);
        } else {
          //직무에 해당 사원이 있는 경우

          console.log("사원 존재");
          var selectJobHtml = "";

          var returnJobHtml = createOption(div, selectJobHtml);
          var selectJobList = "";
          var $selectJobObj = $("#selectJobObj");
          responseObj.forEach((e, i) => {
            selectJobList +=
              "<span>" + e.name + "(" + e.employeeId + ")" + "</span>";
            selectJobList +=
              " " +
              '<select name="selectName" id="seletChange' +
              i +
              '">' +
              '<option value="uncheck">변경직무</option>';
            selectJobList += returnJobHtml;
            selectJobList += "</select>" + "<hr>";
          });
          $selectJobObj.html(selectJobList);
          hasPersonModal(div, responseObj);
        }
      },
    });
    return false;
  });
});

//현재있는 테이블 기준으로 option만들기
function createOption(div, selectJobHtml) {
  let tableArray = insertJobTable();
  for (var i = 0; i < tableArray.length; i++) {
    //현재 선택한 것을 제외하고 option만들기
    if (tableArray[i].jobId.indexOf(div.text().trim()) != -1) {
    } else {
      selectJobHtml +=
        '<option value="' +
        tableArray[i].jobId +
        '">' +
        tableArray[i].jobId +
        "</option>";
    }
  }
  console.log(selectJobHtml);
  return selectJobHtml;
}

//사원이 존재할때의 모달창의 삭제 버튼 클릭시
function deleteJobBtn(div, responseObj, modal) {
  var cnt = 0; //선택 유무 검사 변수
  var showMsg = "";
  var showTrue = false;

  divName = div.text().trim();
  var newEmpIdNJobArr = new Array();
  //선택했는지 안했는지 검사문
  for (var i = 0; i < responseObj.length; i++) {
    if ($("#seletChange" + i + " option:selected").val() != "uncheck") {
      cnt++;
      console.log("선택" + i);
      var newEmpIdNJob = {};
      newEmpIdNJob.employeeId = responseObj[i].employeeId;
      newEmpIdNJob.job = {};
      newEmpIdNJob.job.jobId = $("#seletChange" + i + " option:selected").val();
      newEmpIdNJobArr.push(newEmpIdNJob);
    } else {
      showMsg +=
        JSON.stringify(responseObj[i].name) + "의 변경될 직무를 선택해주세요\n";
      showTrue = true;
    }
  }
  //미선택한 사람들 알람 메세지
  if (showMsg != "" && showTrue == true) {
    alert(showMsg);
  }

  //다 선택했으면, ajax실행
  if (cnt == responseObj.length) {
    modal.classList.add("hidden");
    div.remove(); //ui상 삭제
    isChange = true; //변경 확인
    console.log("ajax준비됬습니다 다선택해서");
    console.log("------------------");
    console.log(newEmpIdNJobArr);
    console.log("------------------");

    return newEmpIdNJobArr;
  }
}

//사원이 존재할때의 모달창
function hasPersonModal(div, responseObj) {
  //모달창 띄우기
  const modal = window.document.getElementById("more_person_modal");
  modal.classList.remove("hidden");
  //직무 글에 채워넣기
  const jobName = modal.querySelector(
    "div.more_modal_content div.card div div.jobName"
  );
  jobName.innerHTML = div.text().trim();

  const overlay = modal.querySelector(".more_modal_overlay");
  console.log(overlay);
  const deleteBtn = modal.querySelector(
    "div.more_modal_content div.card div.mb-3 div button.deleteBtn"
  );
  const cancelBtn = modal.querySelector(
    "div.more_modal_content div.card div.mb-3 div button.btn-secondary"
  );
  const xBoxBtn = modal.querySelector(
    "div.more_modal_content div.card div.card-header button.xBox"
  );

  function xBtn() {
    modal.classList.add("hidden");
  }
  //input에는 없는 overlay 클릭 -> close 기능
  overlay.addEventListener("click", xBtn);
  //삭제 버튼
  deleteBtn.addEventListener("click", function (e) {
    finalNewEmpIdNJobArr = deleteJobBtn(div, responseObj, modal); //삭제 버튼 클릭시 작동
  });
  //취소 버튼
  cancelBtn.addEventListener("click", xBtn);
  //모달창 닫기 버튼
  xBoxBtn.addEventListener("click", xBtn);
}

function hasNotPersonModal(div) {
  //모달창 띄우기
  const modal = window.document.getElementById("zero_person_modal");
  modal.classList.remove("hidden");
  //직무 글에 채워넣기
  const jobName = modal.querySelector(
    "div.zero_modal_content div.card div h5 div.jobName"
  );
  jobName.innerHTML = div.text().trim();

  const overlay = modal.querySelector(".zero_modal_overlay");
  console.log(overlay);
  const deleteBtn = modal.querySelector(
    "div.zero_modal_content div.card div.mb-3 div button.deleteBtn"
  );
  const cancelBtn = modal.querySelector(
    "div.zero_modal_content div.card div.mb-3 div button.btn-secondary"
  );
  const xBoxBtn = modal.querySelector(
    "div.zero_modal_content div.card div.card-header button.xBox"
  );

  function xBtn() {
    modal.classList.add("hidden");
  }
  function deleteJobBtn() {
    console.log("삭제 수행");
    modal.classList.add("hidden");
    div.remove(); //UI상에서만 삭제 -> 실제 저장버튼 눌러야 진짜 저장 실행
  }

  //input에는 없는 overlay 클릭 -> close 기능
  overlay.addEventListener("click", xBtn);
  //삭제 버튼
  deleteBtn.addEventListener("click", deleteJobBtn);
  //취소 버튼
  cancelBtn.addEventListener("click", xBtn);
  //모달창 닫기 버튼
  xBoxBtn.addEventListener("click", xBtn);
}

//직무 추가버튼 관련
function addList() {
  isChange = true; //변경 확인
  var addValue = document.getElementById("addValue").value;
  let tableArray = insertJobTable(); //기존 값데리고오기
  //중복 확인
  duplicate = false;
  for (var i = 0; i < tableArray.length; i++) {
    if (tableArray[i].jobId.indexOf(addValue) != -1) {
      //값이 존재
      console.log("같다");
      duplicate = true;
    }
  }
  if (addValue != "" && duplicate != true) {
    var td = document.createElement("div");
    isChange = true;
    td.setAttribute("div", addValue);
    td.setAttribute(
      "style",
      "padding:30px; display:inline-block;border:1px solid;"
    );
    td.setAttribute("class", "editable");

    var textNode = document.createTextNode(addValue);
    td.appendChild(textNode);

    document.getElementById("jobListObj").appendChild(td);
  } else if (duplicate == true) {
    alert("동일한 직무가 존재합니다!");
  } else if (addValue == "") {
    alert("추가할 직무를 입력해 주세요!");
  }
}

//현재 테이블의 모든 내용 가지고 오기
function insertJobTable() {
  let tableArray = new Array();
  $("#jobListObj").each(function () {
    $(this)
      .children("div")
      .each(function () {
        //json데이터로 생성
        var jsonString = $(this).text().trim();
        var listdata = { jobId: jsonString, jobTitle: jsonString };
        tableArray.push(listdata);
      });
  });
  console.log("받아온 데이터 " + JSON.stringify(tableArray));
  return tableArray;
}
