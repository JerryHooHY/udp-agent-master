% classify_weapon.m
function weapon_type = classify_weapon(equivalent, overpressure_ratio, overpressure_duration, temperature)
    % :
    % equivalent - 
    % overpressure_ratio - 
    % overpressure_duration - 
    % temperature - 
    % : 
    if equivalent > 1000
        weapon_type = '0';  % 1000
        return;  % 
    end    
    % : 
    if overpressure_duration > 8 && temperature > 800 && overpressure_ratio > 2
        weapon_type = '1';  % 
        return;
    end
    % : 
    if equivalent > 0  % 0
        weapon_type = '2';
    else
        weapon_type = '3';  % 
    end
end