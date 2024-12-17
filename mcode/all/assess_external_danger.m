function outside=assess_external_danger(A)
    % 
    % A - 0, 30, 70 
    
    if A > 70
        outside='3';
    elseif A >= 30
        outside='2';
    else
        outside='1';
    end
end
