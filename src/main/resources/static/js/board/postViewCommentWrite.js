document.addEventListener("DOMContentLoaded", () => {
  const commentWriteButton = document.getElementById("commentWriteButton");

  commentWriteButton.addEventListener("click", () => {
    const writer = document.getElementById("commentWriter").value;
    const comment = document.getElementById("commentContents").value;
    const id = [[${post.id}]];

    $.ajax({
      //POST, /comment/save, 작성자/내용/게시글 id
      "type": "post",
      "url": "/comment/save",
      "data": {
        "commentWriter": writer,
        "commentContents": comment,
        "postId": id
      },
      success: res => {
        console.log("요청 성공", res);

        let output = "<table>";
        output += "<th>작성자</th>";
        output += "<th>내용</th>";
        output += "<th>작성시간</th></tr>";
        for (let i in res) {
          output += "<tr>";
          output += "<td>" + res[i].commentWriter + "</td>";
          output += "<td>" + res[i].commentContents + "</td>";
          output += "<td>" + res[i].commentCreatedTime + "</td>";
          output += "</tr>";
        }
        output += "</table>";
        document.getElementById('comment-list').innerHTML = output;
        document.getElementById('commentWriter').value = '';
        document.getElementById('commentContents').value = '';
      },
      error: error => {
        console.log("요청 실패", error);
      }
    });
  }, false);
}, false);