%  - calculate_weighted_score.m
function weighted_score = calculate_weighted_score(weights, indicators)
    % :
    % weights -  (1 x N)
    % indicators -  (1 x N)
    
    % 
    if length(weights) ~= length(indicators)
        error('');
    end
    
    % 
    weighted_score = sum(weights .* indicators);
    
end
