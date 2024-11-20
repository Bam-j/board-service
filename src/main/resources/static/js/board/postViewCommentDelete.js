
document.addEventListener("DOMContentLoaded", () => {
  const commentWriteButton = document.getElementById("commentWriteButton");

  commentWriteButton.addEventListener("click", () => {
    const id = [[${comment.id}]];

    $.ajax({
      //POST, /comment/delete, 작성자/내용/게시글 id
      "type": "post",
      "url": "/comment/delete",
      "data": {
        "commentId": id
      },
      success: res => {
        console.log("요청 성공", res);

        location.reload();
      },
      error: error => {
        console.log("요청 실패", error);
      }
    });
  }, false);
}, false);