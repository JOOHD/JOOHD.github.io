// addItem 수정

import java.util.HashMap;
import java.util.Map;

public class Validation {

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model) {

        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice)
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "validation/v1/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";

        /**
         * 위에 validation을 처리해도, 여전히 남은 문제점이 있따.
         * 1.view template에서 중복 처리가 많다. (비슷비슷한 부분이 많다.)
         * 2.type 오류 처리가 안된다. 숫자 필드는 타입이 Integer이므로 문자 타입 설정이 불가.
         * ㄴ이런 오류들은 오류 발생시, 컨트롤러에 진입하기도 전에 예외 발생, 400예외가 발생한다.
         * 3.고객이 입력 폼에 작성중 오류가 발생하면 발생 부분만 메시지로 보여지고 다른 작성 내용은 남아야 되는데 전체 리셋 되어버림.
         * ㄴ이 말은 Integer 타입의 값은 숫자이므로 문자를 보관 불가 -> 바인딩 불가, 입력 문자 사라짐.
         */


    }

    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, 
                            BindingResult bindingResult
                            RedirectAttributes redirectAttributes, Model model) {

        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError("item" ,"itemName", "상품 이름은 필수입니다.");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError("item", "quantity", "수량은 최대 9,999 까지 허용합니다.");
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice)
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";

        //Bean Validation - HTTP 메시지 컨버터
        //@Validated, HttpMessageConverter (@RequestBody)에도 적용 가능.

    /**
     * @ModelAttribute는 HTTP 요청 파라미터(URL 쿼리 스트리밍, POST Form)를 다룰 때 사용.
     * @RequestBody는 HTTP Body의 데이터를 객체로 변환할 때 사용한다. 주로 API JSON 요청을 다룰 때.
     */

    @Slf4j
    @RestController
    @RequestMapping("/validation/api/items")
    // public class ValidationItemApiController {

    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {

        log.info("API 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("성공 로직 실행");
        return form;

    }
}}
