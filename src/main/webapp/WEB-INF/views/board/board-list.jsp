<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <%@ include file="../include/static-head.jsp" %>

    <style>
        .board-list {
            width: 70%;
            margin: 230px auto 0;
        }

        .board-list .articles {
            margin: 10px auto 100px;
            border-collapse: collapse;
            font-size: 1.5em;
            border-radius: 10px;
        }

        /* 목록 개수별 보기 스타일 */
        .board-list .amount {
            display: flex;
            /* background: skyblue; */
            justify-content: flex-end;
        }

        .board-list .amount li {
            width: 8%;
            margin-right: 10px;
        }

        .board-list .amount li a {
            width: 100%;
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
            background: #333 !important;
            color: #fff !important;
        }

        /* 검색창 */
        .board-list .top-section {
            display: flex;
            justify-content: space-between;
        }

        .board-list .top-section .search {
            flex: 4;
        }

        .board-list .top-section .amount {
            flex: 4;
        }

        .board-list .top-section .search form {
            display: flex;
        }

        .board-list .top-section .search form #search-type {
            flex: 1;
            margin-right: 10px;
        }

        .board-list .top-section .search form input[name=keyword] {
            flex: 3;
        }

        .sortBtn {
            margin-right: 50px;
        }
        .badge {
            margin-left: 30px;
        }
    </style>
</head>

<body>

    <div class="wrap">

        <%@ include file="../include/header.jsp" %>

        <div class="board-list">
            <div class="top-section">
                <!-- 정렬 방식 -->
                <div class="sortBtn">
                    <a href="/board/list?pageNum=1&amount=${pm.page.amount}&sort=Latest&type=${search.type}&keyword=${search.keyword}"><button
                            class="btn btn-primary button-custom" type="button">최신순</button></a>
                    <a href="/board/list?pageNum=1&amount=${pm.page.amount}&sort=Popularity&type=${search.type}&keyword=${search.keyword}"><button
                            class="btn btn-primary button-custom" type="button">인기순</button></a>
                </div>

                <!-- 검색창 영역 -->
                <div class="search">
                    <form action="/board/list" method="get">

                        <select class="form-select" name="type" id="search-type">
                            <option value="title">제목</option>
                            <option value="content">내용</option>
                            <option value="writer">작성자</option>
                            <option value="tc">제목+내용</option>
                        </select>

                        <input type="text" class="form-control" name="keyword" value="${search.keyword}">

                        <button class="btn btn-primary" type="submit">
                            <i class="fas fa-search"></i>
                        </button>
                    </form>
                </div>

                <!-- 목록 보기 개수 -->
                <ul class="amount">
                    <li><a class="btn btn-danger" href="/board/list?amount=10&type=${search.type}&keyword=${search.keyword}">10</a></li>
                    <li><a class="btn btn-danger" href="/board/list?amount=20&type=${search.type}&keyword=${search.keyword}">20</a></li>
                    <li><a class="btn btn-danger" href="/board/list?amount=30&type=${search.type}&keyword=${search.keyword}">30</a></li>
                </ul>
            </div>



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
                        <td title="${b.title}">
                            ${b.shortTitle} [${b.replyCount}]
                            <c:if test="${b.newArticle}">
                                <span class="badge rounded-pill bg-danger">NEW</span>
                            </c:if>
                        </td>
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
                                    href="/board/list?pageNum=${pm.beginPage - 1}&amount=${pm.page.amount}&sort=${pm.page.sort}&type=${search.type}&keyword=${search.keyword}">이전</a>
                            </li>
                        </c:if>


                        <li class="page-item"><a class="page-link"
                                href="/board/list?pageNum=1&amount=${pm.page.amount}&sort=${pm.page.sort}&type=${search.type}&keyword=${search.keyword}">처음으로</a>
                        </li>


                        <c:forEach var="n" begin="${pm.beginPage}" end="${pm.endPage}" step="1">
                            <li data-page-num="${n}" class="page-item"><a class="page-link"
                                    href="/board/list?pageNum=${n}&amount=${pm.page.amount}&sort=${pm.page.sort}&type=${search.type}&keyword=${search.keyword}">${n}</a>
                            </li>
                        </c:forEach>

                        <c:if test="${pm.next}">
                            <li class="page-item"><a class="page-link"
                                    href="/board/list?pageNum=${pm.endPage + 1}&amount=${pm.page.amount}&sort=${pm.page.sort}&type=${search.type}&keyword=${search.keyword}">다음</a>
                            </li>
                        </c:if>

                        <li class="page-item"><a class="page-link"
                                href="/board/list?pageNum=${pm.realEnd}&amount=${pm.page.amount}&sort=${pm.page.sort}&type=${search.type}&keyword=${search.keyword}">마지막으로</a>
                        </li>
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

        // 현재 페이지 active클래스 추가 (페이지 번호에 색 입히기)
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

            location.href = '/board/content/' + bn +
                '?pageNum=${pm.page.pageNum}&amount=${pm.page.amount}&sort=${pm.page.sort}';
        });


        // 옵션태그 고정
        function fixSearchOption() {
            const $select = document.getElementById('search-type');

            for (let $opt of [...$select.children]) {
                if ($opt.value === '${search.type}') {
                    $opt.setAttribute('selected', 'selected');
                    break;
                }
            }
        }

        (function () {

            fixSearchOption();

        })();
    </script>

</body>

</html>