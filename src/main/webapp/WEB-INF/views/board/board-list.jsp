<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <%@ include file="../include/static-head.jsp" %>

    <style>
        .board-list {
            width: 70%;
            margin: 0 auto;
        }

        .board-list .articles {
            margin: 250px auto 100px;
            border-collapse: collapse;
            font-size: 1.5em;
            border-radius: 10px;
        }


        header {
            background: #222;
            border-bottom: 1px solid #2c2c2c;
        }

        /* pagination style */
        .bottom-section {
            margin-top: -50px;
            margin-bottom: 100px;
            display: flex;
        }

        .bottom-section nav {
            flex: 9;
            display: flex;
            justify-content: center;
        }

        .bottom-section .btn-write {
            flex: 1;
        }

        .pagination-custom a {
            color: #444 !important;

        }

        .pagination-custom li.active a,
        .pagination-custom li:hover a {
            background: #333;
            color: #fff !important;
        }

        .wrap {
            position: relative;
        }

        .countBtn {
            position: absolute;
            top: -50px;
            right: 320px;
        }
        .button-custom{
            background: #000;
            color: #fff;
        }

        .sortBtn {
            position: absolute;
            top: -50px;
            left: 320px;
        }

    </style>
</head>

<body>

    <div class="wrap">

        <%@ include file="../include/header.jsp" %>

        <div class="sortBtn">
            <a href="/board/list?pageNum=1&amount=${pm.page.amount}&sort=Latest"><button class="btn btn-primary button-custom" type="button">최신순</button></a>
            <a href="/board/list?pageNum=1&amount=${pm.page.amount}&sort=Popularity"><button class="btn btn-primary button-custom" type="button">인기순</button></a>
        </div> 



        <div class="countBtn">
            <a href="/board/list?pageNum=1&amount=10&sort=${pm.page.sort}"><button class="btn btn-primary button-custom" type="button">10개</button></a>
            <a href="/board/list?pageNum=1&amount=30&sort=${pm.page.sort}"><button class="btn btn-primary button-custom" type="button">30개</button></a>
            <a href="/board/list?pageNum=1&amount=50&sort=${pm.page.sort}"><button class="btn btn-primary button-custom" type="button">50개</button></a>
        </div> 

        


        <div class="board-list">
            <table class="table table-dark table-striped table-hover articles">
                <tr>
                    <th>번호</th>
                    <th>작성자</th>
                    <th>제목</th>
                    <th>조회수</th>
                    <th>작성시간</th>
                </tr>

                <c:forEach var="b" items="${bList}">
                    <tr>
                        <td>${b.boardNo}</td>
                        <td>${b.writer}</td>
                        <td title="${b.title}">${b.shortTitle}</td>
                        <td>${b.viewCnt}</td>
                        <td>${b.prettierDate}</td>
                    </tr>
                </c:forEach>
            </table>
            <!-- 게시글 목록 하단 영역 -->
            <div class="bottom-section">

                <!-- 페이지 버튼 영역 -->
                <nav aria-label="Page navigation example">
                    <ul class="pagination pagination-lg pagination-custom page-num">

                        <c:if test="${pm.prev}">
                            <li class="page-item"><a class="page-link"
                                    href="/board/list?pageNum=${pm.beginPage - 1}&amount=${pm.page.amount}&sort=${pm.page.sort}">이전</a></li>
                        </c:if>

                        
                        <li class="page-item"><a class="page-link"
                            href="/board/list?pageNum=1&amount=${pm.page.amount}&sort=${pm.page.sort}">처음으로</a>
                        </li>
                        

                        <c:forEach var="n" begin="${pm.beginPage}" end="${pm.endPage}" step="1">
                            <li data-page-num="${n}" class="page-item"><a class="page-link" href="/board/list?pageNum=${n}&amount=${pm.page.amount}&sort=${pm.page.sort}">${n}</a></li>
                        </c:forEach>

                        <c:if test="${pm.next}">
                            <li class="page-item"><a class="page-link"
                                    href="/board/list?pageNum=${pm.endPage + 1}&amount=${pm.page.amount}&sort=${pm.page.sort}">다음</a></li>
                        </c:if>
                        
                        <li class="page-item"><a class="page-link"
                            href="/board/list?pageNum=${pm.realEnd}&amount=${pm.page.amount}&sort=${pm.page.sort}">마지막으로</a></li>
                    </ul>
                </nav>


                <!-- 글쓰기 버튼 영역 -->
                <div class="btn-write">
                    <a class="btn btn-outline-danger btn-lg" href="/board/write">글쓰기</a>
                </div>
            </div>

        </div>





        <%@ include file="../include/footer.jsp" %>

    </div>

    <script>
        const msg = '${msg}';
        console.log('msg: ', msg);
        if (msg === 'reg-success') {
            alert('게시물이 정상 등록되었습니다.');
        }

        const pn = '${pm.page.pageNum}';
        console.log('pn: ', pn);
        const a = document.querySelector(".page-num");
        for (let i = 0; i < a.children.length - 1; i++) {
            let b = a.children[i].firstChild.textContent;
            console.log(a.children[i]);
            if (b === pn) {
                a.children[i].classList.toggle("active");
            }
        }



        //상세보기 요청 이벤트
        const $table = document.querySelector(".articles");
        $table.addEventListener('click', e => {
            // th(제목, 글쓴이 등등) 을 클릭했을 때 나가라!
            if (!e.target.matches('.articles td')) return;

            //console.log('tr 클릭됨! - ', e.target);

            // 제목, 글쓴이 등등 중에 무엇을 눌려도 타겟의 부모의 첫번째 자식의 내용
            let bn = e.target.parentElement.firstElementChild.textContent;
            console.log('글번호: ' + bn);

            location.href = '/board/content/' + bn + '?pageNum=${pm.page.pageNum}&amount=${pm.page.amount}&sort=${pm.page.sort}';
        });
    </script>

</body>

</html>