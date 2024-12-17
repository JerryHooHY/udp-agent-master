
function [overall_performance, score_A, B1, B2] = head_performance_evaluation(A1, A2, A3, B11, B12, B13, B14, B15, B16, B21, B22, B23, B24, fourth_level_weights, Third_level_weights, final_weights)
    % 
    % 
    %   A1, A2, A3 -  A1, A2  A3 
    %   B11, B12, B13, B14, B15, B16, B21, B22, B23, B24 - 
    %   fourth_level_weights -  B11, B12, B13, B14, B15, B16, B21, B22, B23, B24
    %   Third_level_weights -  A1, A2, A3, B1, B2
    %   final_weights -  A  B 

    % 
    B11_score = assign_score_B11(B11);  % B11
    B12_score = assign_score_B12(B12);  % B12
    B13_score = assign_score_B13(B13);  % c B13
    B14_score = assign_score_B14(B14);  % B14
    B15_score = assign_score_B15(B15);  % B15
    B16_score = assign_score_B16(B16);  % B16
    B21_score = assign_score_B21(B21);  % B21
    B22_score = assign_score_B22(B22);  % B22
    B23_score = assign_score_B23(B23);  % B23
    B24_score = assign_score_B24(B24);  % B24

    %  A1, A2, A3
    A1_score = assign_score_A1(A1);  % A1
    A2_score = assign_score_A2(A2);  % A2
    A3_score = assign_score_A3(A3);  % A3

    % B1B2
    B1_score = calculate_weighted_score([B11_score, B12_score, B13_score, B14_score, B15_score, B16_score], fourth_level_weights(1:6));   % B1 = B11_score, B12_score, B13_score
    B2_score = calculate_weighted_score([B21_score, B22_score, B23_score, B24_score], fourth_level_weights(7:10)); % B2 = B21_score, B22_score, B23_score, B24_score

    % 
    score_A = calculate_weighted_score([A1_score, A2_score, A3_score], Third_level_weights(1:3));  % A = [A1_score, A2_score, A3_score]
    score_B = calculate_weighted_score([B1_score, B2_score], Third_level_weights(4:5));      % B = [B1, B2]

    % 
    overall_performance = calculate_weighted_score([score_A, score_B], final_weights(1:2));  %  = [A, B]

    
    B1 = B1_score;
    B2 = B2_score;
       
    % 
    assignin('base', 'B1', B1);
    assignin('base', 'B2', B2);
    assignin('base', 'score_A', score_A);
    assignin('base', 'score_B', score_B);
    assignin('base', 'overall_performance', overall_performance);

end

% B11
function score = assign_score_B11(B11)
    if B11 < 2
        score = 0;
    elseif B11 >= 2 && B11 <= 15
        score = 30;
    elseif B11 > 15 && B11 <= 40
        score = 70;
    else
        score = 100;
    end
end

% B12
function score = assign_score_B12(B12)
    if B12 < 0.2
        score = 0;
    elseif B12 >= 0.2 && B12 <= 5
        score = 30;
    elseif B12 > 5 && B12 <= 50
        score = 70;
    else
        score = 100;
    end
end

% c B13
function score = assign_score_B13(B13)
    if B13 > 50
        score = 0;
    elseif B13 >= 3.0 && B13 <= 5.0
        score = 30;
    elseif B13 >= 1.0 && B13 < 3.0
        score = 70;
    else
        score = 100;
    end
end

% B14
function score = assign_score_B14(B14)
    if B14 < 150
        score = 0;
    elseif B14 >= 150 && B14 <= 1000
        score = 30;
    elseif B14 > 1000 && B14 <= 4000
        score = 70;
    else
        score = 100;
    end
end

% B15
function score = assign_score_B15(B15)
    if B15 < 0.15
        score = 0;
    elseif B15 >= 0.15 && B15 <= 0.2
        score = 30;
    elseif B15 > 0.2 && B15 <= 0.25
        score = 70;
    else
        score = 100;
    end
end

% B16B11
function score = assign_score_B16(B16)
    % B16B11
    if B16 < 2
        score = 0;
    elseif B16 >= 2 && B16 <= 15
        score = 30;
    elseif B16 > 15 && B16 <= 40
        score = 70;
    else
        score = 100;
    end
end

% B21
function score = assign_score_B21(B21)
    if B21 < 0.15
        score = 0;
    elseif B21 >= 0.15 && B21 <= 0.2
        score = 30;
    elseif B21 > 0.2 && B21 <= 0.25
        score = 70;
    else
        score = 100;
    end
end

% B22
function score = assign_score_B22(B22)
    if B22 < 150
        score = 0;
    elseif B22 >= 150 && B22 <= 1000
        score = 30;
    elseif B22 > 1000 && B22 <= 2000
        score = 70;
    else
        score = 100;
    end
end

% B23
function score = assign_score_B23(B23)
    if B23 < 3
        score = 0;
    elseif B23 >= 3 && B23 <= 4
        score = 30;
    elseif B23 > 4 && B23 <= 7
        score = 70;
    else
        score = 100;
    end
end

% B24
function score = assign_score_B24(B24)
    if B24 < 2
        score = 0;
    elseif B24 >= 2 && B24 <= 15
        score = 30;
    elseif B24 > 15 && B24 <= 40
        score = 70;
    else
        score = 100;
    end
end

% A1
function score = assign_score_A1(A1)
    score = min(100 / A1, 100);  % =100m/, 100
end

% A2
function score = assign_score_A2(A2)
    score = min(A2 / 10, 100);  % =Kg/10, 100
end

% A3
function score = assign_score_A3(A3)
    if A3 == "0"
        score = 100;
    elseif A3 == "1"
        score = 70;
    elseif A3 == "2"
        score = 30;
    else
        score = 0;
    end
end

