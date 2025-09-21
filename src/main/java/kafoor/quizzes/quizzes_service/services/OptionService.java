package kafoor.quizzes.quizzes_service.services;

import kafoor.quizzes.quizzes_service.dtos.OptionCreateReqDTO;
import kafoor.quizzes.quizzes_service.dtos.OptionUpdateReqDTO;
import kafoor.quizzes.quizzes_service.exceptions.NotFound;
import kafoor.quizzes.quizzes_service.models.Option;
import kafoor.quizzes.quizzes_service.repositories.OptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    @Autowired
    private OptionRepo optionRepo;

    public List<Option> findAllOptionsOfQuestion(long questionId){
        return optionRepo.findByQuestion(questionId);
    }

    public Option findOptionById(long id){
        return optionRepo.findById(id).orElseThrow(() -> new NotFound("Option not found"));
    }

    public Option createOption(OptionCreateReqDTO dto){
        Option newOption = Option.builder()
                .text(dto.getText())
                .build();
        return optionRepo.save(newOption);
    }

    public Option updateOption(OptionUpdateReqDTO dto){
        Option option = findOptionById(dto.getId());
        if(dto.getText() != null && dto.getText().isEmpty()) option.setText(dto.getText());
        return optionRepo.save(option);
    }

    public void deleteOptionById(long id){
        if(optionRepo.existsById(id)) throw new NotFound("Option not found");
        optionRepo.deleteById(id);
    }
}
