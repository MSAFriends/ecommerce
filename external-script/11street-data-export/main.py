import requests
import xmltodict
import csv
from configuration import API_CODE, API_SECRET_KEY

ELEVEN_STREET_API_URL = "http://openapi.11st.co.kr/openapi/OpenApiService.tmall"
PAGE_SIZE = 10
TOTAL_PAGE_NUM = 10

def get_topics():
    topics = [
        "자전거", "유모차", "청소기", "제습기", "선반", "책상", "의자", "전자렌지", "tv", "킥보드",
        "아이폰", "냉장고", "가방", "목걸이", "갤럭시", "시계", "침낭", "밥솥", "후드티", "커피",
        "다시마", "고추장", "인형", "세탁기", "옷걸이", "신발", "침대", "노트북", "커피", "음료",
        "감자", "고구마", "간장", "고기", "과자", "선", "식물", "취미", "공부", "책",
        "TV", "스마트폰", "노트북", "의류", "신발", "가방", "가전제품", "가구", "화장품",
        "스포츠용품", "주방용품", "식품", "음료", "문구", "도서", "컴퓨터", "운동기구", "자동차용품",
        "화장품", "스킨케어", "메이크업", "향수", "세탁세제", "주방세제", "바디워시", "샴푸", "기초화장품",
        "애완용품", "애완동물사료", "애완동물용품", "전자제품", "카메라", "휴대폰액세서리", "컴퓨터액세서리", "주방가전",
        "건강식품", "다이어트용품", "영양제", "운동용품", "스포츠의류", "스포츠화", "스포츠용품", "자전거용품",
        "아기용품", "유모차", "기저귀", "분유", "유아동의류", "유아동신발", "유아동용품", "유아동장난감",
        "가구", "침대", "소파", "식탁", "수납장", "책상", "의자", "화장대", "수납가구",
        "주방용품", "조리도구", "식기", "주방수납", "양식용품", "주방가전", "수납용품", "수건", "침구",
        "생활용품", "청소용품", "세탁용품", "인테리어소품", "화장지", "욕실용품", "욕실수납", "의약외품", "구강용품",
        "의료용품", "건강케어용품", "핸드크림", "스킨로션", "선스크린", "마스크팩", "색조메이크업", "베이스메이크업", "아이메이크업",
        "남성화장품", "남성스킨케어", "남성선물세트", "남성향수", "세탁세제", "주방세제", "화장지", "샴푸", "바디워시",
        "고양이용품", "강아지용품", "애완동물사료", "애완동물용품", "애완용품", "애견간식", "애견의류", "애견장난감", "고양이간식",
        "고양이의류", "고양이장난감", "바디워시", "샴푸", "토너", "로션", "에센스", "페이스크림", "크림", "선스크린",
        "향수", "립스틱", "마스크팩", "메이크업브러쉬", "네일", "스킨케어세트", "메이크업세트", "색조메이크업", "베이스메이크업", "아이메이크업",
        "TV", "스마트폰", "노트북", "태블릿PC", "게임기", "오디오", "냉장고", "세탁기", "청소기", "에어컨",
        "전자렌지", "커피머신", "믹서기", "전기밥솥", "토스트기", "전기포트", "가습기", "제습기", "공기청정기", "운동기구",
        "건강용품", "다이어트용품", "헬스용품", "헬스의류", "헬스화", "헬스용품", "자전거용품", "스키용품", "스케이트보드",
        "배드민턴용품", "등산용품", "골프용품", "축구용품", "야구용품", "수영용품", "자동차용품", "차량용시트", "차량용매트", "차량용바디커버",
        "주유기", "카시트", "공기청정기", "필터", "향균제", "차량용세정용품", "LED램프", "자동차방향제", "차량용청소용품", "차량용태그"
    ]
    return topics

def convert_data_to_csv(data: list, csv_file_name: str) -> None:
    headers = ["id"] + list(data[0].keys())
    index = 0
    f = open(csv_file_name + ".csv", "w")
    writer = csv.writer(f)
    writer.writerow(headers)
    for row in data:
        index += 1
        writer.writerow([index] + list(row.values()))
    f.close()

def get_products(url: str, search_keyword: str, page_size: int, page_num: int) -> list:
    data = []
    for i in range(1, page_num+1):

        params = {
            "key": API_SECRET_KEY,
            "apiCode": API_CODE,
            "keyword": search_keyword,
            "pageSize": page_size,
            "pageNum": i
        }
        products = requests.get(url, params=params)
        print(len(products.text), PAGE_SIZE)
        if len(products.text) > 500:
            result = xmltodict.parse(products.text)
            data += result["ProductSearchResponse"]["Products"]["Product"]
            print("keyword: ", search_keyword, " Num: ", i)
    return data

total_data = []
for topic in get_topics():
    total_data += get_products(ELEVEN_STREET_API_URL, topic, PAGE_SIZE, TOTAL_PAGE_NUM)
convert_data_to_csv(total_data, "out")
