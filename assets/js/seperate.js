function resetCategoryList(){ // depth01, 02 데이터 뿌리기

    $ajax({
        type : 'post',
        url  : '/archive/categoryList.html',
        success : function(result) {

        //{'categorySeq" : "26", "categoryName" : "디자인사업분류", "categoryUpSeq" : "1"}
        //depth01 대분류

        /*상세검색-디자인사업분류 대분류*/
        //서버에서 받아온 데이터 중 'list'라는 속성의 값을 List에 저장, 이 변수에 카테고리 리스트 데이터가 배열로 담긴다.
        //categoryDepth01 = subCategoriesOptions 두 개의 변수는 같은 기능
        var categoryList = result.list;
        var categoryDepth01 = '<input type="hidden" id="detail_depth01"  name="detail_depth01" value="" /><a href="javascript:;" class="sTypeBtn">대분류를 선택해주세요.</a><ul class="common_filter_list" id="category_depth01">';
        var categoryCultureList = [];

            //categoryList를 반복문으로 돌리면서 대분류와 중분류 카테고리를 구분하여 처리하는 코드
            for(var i = 0; i < categoryList.length; i++) {
                var categorySeq = categoryList[i].categorySeq;
                var categoryName = categoryList[i].categoryName;
                var categoryUpSeq = categoryList[i].categoryUpSeq;

                /**
                 * 즉, categoryCultureList 배열은 categoryList 배열 중에서 
                 * "categoryUpSeq" 값이 '26'인 요소들만을 가지고 있는 새로운 배열을 형성합니다. 
                 * 이렇게 필터링된 중분류 데이터들이 categoryCultureList 배열에 담기게 됩니다. 
                 * 이렇게 수행된 결과로 categoryCultureList 배열은 "디자인사업분류"에 해당하는 중분류 데이터들을 담게 됩니다.
                 */
                if(categoryUpSeq == '26') {
                    categoryDepth01 +='<li><a href="javascript:;" class="sType depth01" data-no="'+categorySeq+'">'+categoryName+'</a></li>';
                    categoryCultureList.push(categoryList[i]); //categoryUpSeq 값이 '26'인 요소를 배열에 추가하는 작업

                    //push로 categoryUpSeq == '26'이 다 채워지면 대분류 select box가 완성된다.
                }

                //대분류 영역을 닫기 위해 </ul> 태그를 categoryDepth01 문자열에 추가합니다.
                categoryDepth01 += '</ul>';
                $('#category_depth01_box').empty(); //대분류 영역을 업데이트하기 전에 해당 영역을 비웁니다.
                $('#category_depth01_box').html(categoryDepth01); //대분류 영역을 새로운 HTML 문자열인 categoryDepth01로 업데이트합니다.

                //중분류 리셋
                $('#category_depht02').empty(); // 중분류 영역을 업데이트하기 전에 해당 영역을 비웁니다.
                $('#detail_depth02').val(""); // 중분류를 선택하지 않았으므로, 중분류 값을 비웁니다.

                /*상세검색-연도*/
                var categoryYearDepth01="";
                var categoryYearDepth02="";
                var categoryYearList = [];

                for(var i = 0; i < categoryList.length; i++) {
                    var categorySeq = categoryList[i].categorySeq;
                    var categoryName = categoryList[i].categoryName;
                    var categoryUpSeq = categoryList[i].categoryUpSeq;

                    /**
                     * 이후 반복이 마지막 요소인 
                     * { yearSeq: 2014, yearName: "2014년", yearUpSeq: "9" }까지 진행되며, 
                     * 이때 조건문은 categoryList[i].yearSeq != categoryList[categoryList.length-1].yearSeq이 성립하지 않게 됩니다. 
                     * 따라서 { yearSeq: 2014, yearName: "2014년", yearUpSeq: "9" }는 catagoryYearDepth01에 추가되지 않습니다.
                     */
                    if(categoryUpSeq == '9'){
                        //낮은 연도는 가장 높은 연도를 제외 시킨다.
                        if(categoryList[i].categorySeq != categoryList[categoryList.length-1].categorySeq){
                            catagoryYearDepth01	+='<li><a href="javascript:;"  class="sType depthYear01_under" data-no="'+categorySeq+'" >'+categoryName+'</a></li>';
                        }
                    
                        //높은 연도는 가장 낮은 연도를 제외시킨다.
                        if(categorySeq != '10'){
                            catagoryYearDepth02	+='<li><a href="javascript:;"  class="sType depthYear01_high" data-no="'+categorySeq+'" >'+categoryName+'</a></li>';
                        }
                        categoryYearList.push(categoryList[i]);
                    }
                }
            }
        }  
    })
}