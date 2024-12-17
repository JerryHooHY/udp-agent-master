function pso=PSO(file_dir)
    data_file = 'data_latest.txt';  %  'data_latest.txt'
    weight_file = 'level_weights.xlsx';
    file_path = fullfile(file_dir, data_file);
    weight_path = fullfile(file_dir, weight_file);

    % 
    try
        %  readtable 
        file_data = readtable(file_path, 'Delimiter', '\t', 'VariableNamingRule', 'preserve');
        
        % 
        data_matrix = table2array(file_data);
        
        % 
        time = data_matrix(:, 1);  % 
        [A32, A34, B11, B12, B14, B15, B21, B22, B24] = deal( ...
            data_matrix(:, 10),  ... % 
            data_matrix(:, 11),  ... % 
            data_matrix(:, 12),  ... % 
            data_matrix(:, 13),  ... % 
            data_matrix(:, 14),  ... % 
            data_matrix(:, 15),  ... % 
            data_matrix(:, 16),  ... % 
            data_matrix(:, 17),  ... % 
            data_matrix(:, 18));    % 
        
    %% A33B16B23
        % 
        %threshold_value_ A33= 40;
        %threshold_value_ B16= 40;
        %threshold_value_ B23= 40;

        %  analyze_explosion 
        % result_A33 = analyze_explosion(example_data_A33, threshold_value_A33);
        % result_B16 = analyze_explosion(example_data_B16, threshold_value_B16);
        % result_B23 = analyze_explosion(example_data_B23, threshold_value_B23);
        
        %A33B16B23
        %A33 = resultA33.duration; %A33
        %B16 = resultB16.duration; %B16
        %B23 = resultB23.duration; %B23
    

        
        % 
        A32 = process_sensor_data([time, A32]);
        A34 = process_sensor_data([time, A34]);
        B11 = process_sensor_data([time, B11]);
        B12 = process_sensor_data([time, B12]);
        B14 = process_sensor_data([time, B14]);
        B15 = process_sensor_data([time, B15]);
        B21 = process_sensor_data([time, B21]);
        B22 = process_sensor_data([time, B22]);
        B24 = process_sensor_data([time, B24]);

    catch ME
        % 
        fprintf('Error processing file %s: %s\n', data_file, ME.message);
    end

    %% PSO_Location_and_WeaponEquivalent[best_g, best_g_2]best_gbest_g_2
        % PSO_Location_and_WeaponEquivalent
    [best_g, best_g_2] = PSO_Location_and_WeaponEquivalent( file_path) ;
        %load('PSO_Location_and_WeaponEquivalent.mat', 'best_g', 'best_g_2');  % 
        A1 = sqrt((best_g(1))^2 + (best_g(2))^2);  %A1=sqrt((best_g(1))^2+(best_g(2))^2)
        A2 = best_g_2(2);  %A2=best_g_2(2)
        B13 = 1000*best_g(3);  %B13=best_g(3)
        A31 = A2; %A31A31A2
    

    %% 31
        A33 = 15; %A33
        B16 = 0.0225; %B16
        B23 = 0.0047; %B23

    try
        %  Excel 
        level_weights_data = readtable(weight_path, 'ReadRowNames', false);
        
        %  fourth_level_weights
        fourth_level_weights = level_weights_data{1, :};  % 
        
        %  Third_level_weights
        third_level_weights = level_weights_data{2, :};  % 
        
        %  final_weights
        final_weights = level_weights_data{3, :};  % 
        
        % 
        assignin('base', 'fourth_level_weights', fourth_level_weights);
        assignin('base', 'third_level_weights', third_level_weights);
        assignin('base', 'final_weights', final_weights);
        
    catch ME
        %  Excel 
        fprintf('Error processing level_weights.xlsx: %s\n', ME.message);
    end

    weapon_type = main_classify_weapon(A31, A32, A33, A34);
    A3 = weapon_type ;  %A3

    %%  head_performance_evaluation 
    [overall_performance, score_A, B1, B2] = head_performance_evaluation(A1, A2, A3, B11, B12, B13, B14, B15, B16, B21, B22, B23, B24, ...
        fourth_level_weights, third_level_weights, final_weights);

    ret.outside = assess_external_danger(score_A);
    ret.explosion.x = best_g(1);
    ret.explosion.y = best_g(2);
    ret.explosion.weapon_type = weapon_type;
    ret.explosion.equivalent = A31;
    ret.structure = B1;
    ret.device = B2;
    ret.overall = overall_performance;

    pso = jsonencode(ret);
    % disp(pso)
