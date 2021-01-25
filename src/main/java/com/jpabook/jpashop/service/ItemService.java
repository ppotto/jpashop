package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 데이터베이스 수정시, 변경 감지 기능을 사용하는 방법

    //즉,,,, 준영속 엔티티를 가지고 영속성 컨텍스트에서 찐을 찾아와서 데이터 수정해서 넣음
    // -> 영속성 컨텍스트에 있는건 더티 체킹 일어나므로,,,,,,
    // 이와 같은 기능을 merge 가 똑같이 수행한다.
    // 모든 데이터를 바꿔치기함 ..

    // merge가 반환하는 엔티티는 영속성 컨텍스트에 등록되잇음

    // 변경 감지 기능     vs      merge
    // 부분 수정                전체 필드 값 채워넣음
    // 수정한 값 이외
    // 필드 값 유지             필드 값이 없는 경우 null 이 될 수 있음.....

    // 결론 : 절대 merge는 사용하지 말것,,,,
    // 영속성 컨텍스트로 찐 불러와서 직접 수정 하쇼
    @Transactional
    public void updateItem(Long itemId, Book bookParam) {
        //파라미터로 받은 Book bookParam은 준영속상태의 엔티티이고,
        // findOne을 통해 가져온 findItem
        //얘는 영속상태임 즉, dirty checking이 일어남.
        Item findItem = itemRepository.findOne(itemId);

        //-> 의미있는 메소드 하나 생성해서 수정할것 아마 domain에다가 비즈니스 로직 몰아넣으니까
        // 거기에....작성하면될듯,,,,?? 아닌가 service 에다가 넣는건가
        findItem.setPrice(bookParam.getPrice());
        findItem.setName(bookParam.getName());
        findItem.setStockQuantity(bookParam.getStockQuantity());
    }


    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItem() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}

