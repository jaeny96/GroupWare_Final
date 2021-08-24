//부서 아이디
var deptId = new Array();
//부서 이름
var deptName = new Array();
//부서 Obj 내 추가할 buttonClass 명
var buttonClassName = new Array();
//부서 Obj 내 추가할 tbodyClass 명
var tBodyClassName = new Array();
//해당 사원이름 붙일 테이블 명
var tableTarget = new Array();
//부서별 사원 조회할 backurl
var backurlEmp = new Array();

function setClassName(i, value) {
  tBodyClassName[i] = value + "Tbody";
  buttonClassName[i] = "apLine" + value;
}

function createDeptObj(buttonClass, tBodyClass, deptNameText, target) {
  var button = document.createElement("button");
  button.setAttribute("type", "button");
  button.setAttribute("class", "btn btn-success text-left");
  button.classList.add(buttonClass);
  button.setAttribute("style", "width: 200px");
  button.setAttribute("id", "agreementBoxBtn");
  button.innerHTML = deptNameText + "<span class='fa fa-sort-down'></span>";

  var table = document.createElement("table");
  table.setAttribute("class", "table mb-0");
  table.setAttribute("style", "width: 200px");

  var tbody = document.createElement("tbody");
  tbody.setAttribute("class", tBodyClass);
  table.appendChild(tbody);

  target.append(button);
  target.append(table);
}

//스태프 이름
var apLineStaffName = new Array();
//스태프 사번
var apLineStaffId = new Array();

var apLineStaffDept = new Array();

var tbody = document.querySelector(".CEOTbody");

function apStaffNameClickHandler(e) {
  localStorage.setItem("apLineName", e.target.innerText);
  localStorage.setItem("apLineId", e.target.id);
}
function createApLineElement(staffName, staffId, target) {
  var tr = document.createElement("tr");
  var td = document.createElement("td");
  tr.setAttribute("class", "listInner");
  tr.setAttribute("onclick", "javascript:getNameForApLine(this);");
  td.setAttribute("class", "apStaffName");
  td.innerHTML = staffName;
  td.setAttribute("id", staffId);
  td.addEventListener("click", apStaffNameClickHandler);
  tr.appendChild(td);
  target.append(tr);
}

$(function () {
  var $divTarget = $("div#apLineCardbody>div");
  //--ajax : 부서번호 및 부서명 가져오기
  var backurlDept = "http://localhost:8888/gwback/approval/draft/ap-line";
  $.ajax({
    url: backurlDept,
    method: "get",
    success: function (responseData) {
      $(responseData).each(function (i, e) {
        deptId[i] = e.departmentId;
        deptName[i] = e.departmentTitle;
      });
      for (var i = 0; i < deptId.length; i++) {
        setClassName(i, deptId[i]);
      }
      for (var i = 0; i < deptId.length; i++) {
        createDeptObj(
          buttonClassName[i],
          tBodyClassName[i],
          deptName[i],
          $divTarget
        );
      }

      //--부서별 사원 가져오기
      for (var i = 0; i < deptId.length; i++) {
        backurlEmp[i] =
          "http://localhost:8888/gwback/approval/draft/ap-line/" + deptId[i];
      }
      for (var i = 0; i < deptId.length; i++) {
        showEmpByDept(backurlEmp[i]);
      }
    },
  });
});

function showEmpByDept(url) {
  $.ajax({
    url: url,
    method: "get",
    success: function (responseData) {
      apLineStaffName = [];
      apLineStaffId = [];
      apLineStaffDept = [];
      tableTarget = [];
      //스태프 부서번호
      $(responseData).each(function (i, e) {
        apLineStaffName[i] = e.name;
        apLineStaffId[i] = e.employeeId;
        apLineStaffDept[i] = e.department.departmentId;
      });

      for (var j = 0; j < apLineStaffDept.length; j++) {
        tableTarget[j] = $(
          "div#apLineCardbody>div>table>tbody." + apLineStaffDept[j] + "Tbody"
        );
      }
      for (var j = 0; j < apLineStaffName.length; j++) {
        createShowEmpByDept(
          apLineStaffName[j],
          apLineStaffId[j],
          tableTarget[j]
        );
      }
    },
  });
}

function createShowEmpByDept(apLineStaffName, apLineStaffId, target) {
  createApLineElement(apLineStaffName, apLineStaffId, target);
}
