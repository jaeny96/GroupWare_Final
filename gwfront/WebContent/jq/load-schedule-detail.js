// //수정
// $(function () {
//   // var skdno = localStorage.getItem("skdNo");
//   // console.log(skdno);
//   $("[class*='deleteBtn']").click(function () {
//     var backurlDeleteSkd = "/gwback/schedule/remove/" + localStorage.getItem("skdNo");
//     $.ajax({
//       url: backurlDeleteSkd,
//       method: "DELETE",
//       transformRequest: [null],
//       transformResponse: [null],
//       jsonpCallbackParam: "callback",
//       headers: {
//         Accept: "application/json, text/plain, */*",
//       },
//       success: function () {
//         alert("일정이 삭제되었습니다!");
//         location.reload();
//         // loadSchedule();
//       },
//     });
//     // e.preventDefault();
//   });

//   var backSkdModify = "/gwback/schedule/modify/" + skdno;

//   //var modifySkdFormObj = $("#modifySkdContent");
//   var modifySkdSubmitBtn = $("button.modifySkdSubmit");
//   var skdUpdateTypeObj = $("#skdUpdateTypeSelect"); //problem

//   var skdUpdateTypeValue = "업무";
//   skdUpdateTypeObj.change(function () {
//     console.log(this.value);
//     skdUpdateTypeValue = this.value + "";
//   });

//   var skdUpdateTitle = $("#update_title");
//   var skdUpdateContent = $("#input_content_update"); //내용
//   var skdUpdateStartDate = $("#start_date_update"); //
//   var skdUpdateStartTime = $("#start_time_update"); //
//   var skdUpdateEndDate = $("#end_date_update"); //종료날짜
//   var skdUpdateEndTime = $("#end_time_update"); //종료시간
//   var skdUpdateShare2 = $('input[name="radio-2"]'); //problem
//   var currentSkdNo = localStorage.getItem("skdNo");

//   var test = "p";
//   skdUpdateShare2.change(function () {
//     console.log(this.value);
//     test = this.value;
//     console.log(test);
//   });

//   $("#skdModifyBtn").click(function () {
//     var UpdatePreTitleValue = localStorage.getItem("title");
//     var UpdatePreStartDate = localStorage.getItem("startDate");
//     var UpdatePreStartTime = localStorage.getItem("startTime");
//     var skdOriginEndDate = localStorage.getItem("endDate");
//     var skdOriginEndTime = localStorage.getItem("endTime");
//     var UdpatePreContentValue = localStorage.getItem("content");

//     //test용 프린트
//     console.log(UpdatePreTitleValue);
//     console.log(UpdatePreStartDate);
//     console.log(UpdatePreStartTime);
//     console.log(skdOriginEndDate);
//     console.log(skdOriginEndTime);
//     console.log(UdpatePreContentValue);

//     //기존 상세내역에 있었던 내용을 input에 넣기
//     skdUpdateTitle.attr("value", UpdatePreTitleValue);
//     skdUpdateStartDate.val(UpdatePreStartDate);
//     skdUpdateStartTime.val(UpdatePreStartTime);
//     skdUpdateEndDate.val(skdOriginEndDate);
//     skdUpdateEndTime.val(skdOriginEndTime);
//     skdUpdateContent.val(UdpatePreContentValue);
//     // skdUpdateType.val(UdpatePreTypeValue);
//   });

//   $("[class*='modifySkdSubmit']")
//     .off("click")
//     .on("click", function () {
//       // let checker = $._data($("[class*='modifySkdSubmit']")[0], "events");
//       // console.log(checker);
//       //일정번호를 기준으로 수정하는 것이기 때문에 skd_no가 반드시 필요하다
//       if (teamOrPersonalOption == "p") {
//         $.ajax({
//           url: backSkdModify,
//           method: "PUT",
//           transformRequest: [null],
//           transformResponse: [null],
//           jsonpCallbackParam: "callback",
//           headers: {
//             Accept: "application/json, text/plain, */*",
//             "Content-Type": "application/json;charset=utf-8",
//           },
//           data: JSON.stringify({
//             skdType: skdUpdateTypeValue,
//             skdTitle: skdUpdateTitle.val(),
//             skdContent: skdUpdateContent.val(),
//             skdStartDate:
//               skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),
//             skdEndDate: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
//             skdShare: teamOrPersonalOption,
//           }),

//           success: function () {
//             window.alert("일정이 변경되었습니다");

//             //scheduleMenu로 돌아가는 트리거 이벤트
//             //loadSchedule();
//             //location.reload();
//           },
//           error: function (request, status, error) {
//             alert(
//               "code:" +
//                 request.status +
//                 "\n" +
//                 "message:" +
//                 request.responseText +
//                 "\n" +
//                 "error:" +
//                 error
//             );
//           },
//         });

//         //팀일정일 경우
//       } else if (teamOrPersonalOption == "t") {
//         $.ajax({
//           url: backSkdModify,
//           method: "PUT",
//           transformRequest: [null],
//           transformResponse: [null],
//           jsonpCallbackParam: "callback",
//           headers: {
//             Accept: "application/json, text/plain, */*",
//             "Content-Type": "application/json;charset=utf-8",
//           },
//           data: JSON.stringify({
//             skdType: skdUpdateTypeValue,
//             skdTitle: skdUpdateTitle.val(),
//             skdContent: skdUpdateContent.val(),
//             skdStartDate:
//               skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),
//             skdEndDate: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
//             skdShare: teamOrPersonalOption,
//           }),
//           success: function () {
//             window.alert("2일정이 변경되었습니다");
//             //loadSchedule();
//             // location.reload();
//           },
//           error: function (request, status, error) {
//             alert(
//               "code:" +
//                 request.status +
//                 "\n" +
//                 "message:" +
//                 request.responseText +
//                 "\n" +
//                 "error:" +
//                 error
//             );
//           },
//         });
//         //e.preventDefault();
//       }
//     });
//   //   function modifySkdSubmitHandler(e) {
//   //     // console.log(skdUpdateShare);

//   //     if (test == "p") {
//   //       $.ajax({
//   //         url: backSkdModify,
//   //         method: "PUT",
//   //         transformRequest: [null],
//   //         transformResponse: [null],
//   //         jsonpCallbackParam: "callback",
//   //         headers: {
//   //           Accept: "application/json, text/plain, */*",
//   //           "Content-Type": "application/json;charset=utf-8",
//   //         },
//   //         data: JSON.stringify({
//   //           skdType: skdUpdateTypeValue,
//   //           skdTitle: skdUpdateTitle.val(),
//   //           skdContent: skdUpdateContent.val(),
//   //           skdStartDate:
//   //             skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),

//   //           skdEndDate: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
//   //           skdShare: teamOrPersonalOption,
//   //         }),

//   //         success: function () {
//   //           window.alert("일정이 변경되었습니다");
//   //           //$("#scheduleMenu").click();
//   //           // location.reload();
//   //           // $(function () {
//   //           //   $("#scheduleMenu")
//   //           //     .click(function () {
//   //           //       this.click();
//   //           //     })
//   //           //     .click();
//   //           // });
//   //         },
//   //         error: function (request, status, error) {
//   //           alert(
//   //             "code:" +
//   //               request.status +
//   //               "\n" +
//   //               "message:" +
//   //               request.responseText +
//   //               "\n" +
//   //               "error:" +
//   //               error
//   //           );
//   //         },
//   //       });
//   //       // e.preventDefault();
//   //     } else if (test == "t") {
//   //       $.ajax({
//   //         url: backSkdModify,
//   //         method: "PUT",
//   //         transformRequest: [null],
//   //         transformResponse: [null],
//   //         jsonpCallbackParam: "callback",
//   //         headers: {
//   //           Accept: "application/json, text/plain, */*",
//   //           "Content-Type": "application/json;charset=utf-8",
//   //         },
//   //         data: JSON.stringify({
//   //           skdType: skdUpdateTypeValue,
//   //           skdTitle: skdUpdateTitle.val(),
//   //           skdContent: skdUpdateContent.val(),
//   //           skdStartDate:
//   //             skdUpdateStartDate.val() + " " + skdUpdateStartTime.val(),

//   //           skdEndDate: skdUpdateEndDate.val() + " " + skdUpdateEndTime.val(),
//   //           skdShare: teamOrPersonalOption,
//   //         }),
//   //         success: function () {
//   //           window.alert("일정이 변경되었습니다");
//   //           //$("#scheduleMenu").click();
//   //           //location.reload();
//   //           // $(function () {
//   //           //   $("#scheduleMenu")
//   //           //     .click(function () {
//   //           //       this.click();
//   //           //     })
//   //           //     .click();
//   //           // });
//   //         },
//   //         error: function (request, status, error) {
//   //           alert(
//   //             "code:" +
//   //               request.status +
//   //               "\n" +
//   //               "message:" +
//   //               request.responseText +
//   //               "\n" +
//   //               "error:" +
//   //               error
//   //           );
//   //         },
//   //       });
//   //       //e.preventDefault();
//   //     }
//   //   }

//   //   modifySkdSubmitBtn.click(modifySkdSubmitHandler);

//   //   //삭제

//   //   var skdModifyModalObj = document.getElementById("skdDetail");
//   //   var skdDeleteBtn = skdModifyModalObj.querySelector("button.deleteBtn");
//   //   console.log(skdDeleteBtn);

//   //   var currentSkdNo = localStorage.getItem("skdNo");
//   //   console.log("clickedskdno" + currentSkdNo);
//   //   var backurlDeleteSkd = "/gwback/schedule/remove/" + currentSkdNo;

//   //   //여기서부터..

//   //   //function skdDeleteClickHandler(e) {
//   //   $("[class*='deleteBtn']").click(function () {
//   //     $.ajax({
//   //       url: backurlDeleteSkd,
//   //       method: "DELETE",
//   //       transformRequest: [null],
//   //       transformResponse: [null],
//   //       jsonpCallbackParam: "callback",
//   //       headers: {
//   //         Accept: "application/json, text/plain, */*",
//   //       },
//   //       success: function () {
//   //         alert("일정이 삭제되었습니다!");
//   //         //$("#scheduleMenu").click();
//   //         // location.reload();
//   //         // $(function () {
//   //         //   $("#scheduleMenu")
//   //         //     .click(function () {
//   //         //       this.click();
//   //         //     })
//   //         //     .click();
//   //         // });
//   //       },
//   //     });
//   //     //e.preventDefault();
//   //   });

//   //   //skdDeleteBtn.addEventListener("click", skdDeleteClickHandler);
// });
