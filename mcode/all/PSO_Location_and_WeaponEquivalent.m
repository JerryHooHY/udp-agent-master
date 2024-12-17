function [best_g, best_g_2] = PSO_Location_and_WeaponEquivalent( file_path)
%% Step 1: 
measurepoint = [59.796 52.6298; 86.8346 53.3475; 86.306 27.4298; 87.9909 2.043;
                63.3797 1.9304; 16.105 2.2048; 12.6512 24.1331; 9.2838 52.0662];
long_m = length(measurepoint); %

data_a = readmatrix(file_path);

time = data_a(:, 1);
% 
maxTime = zeros(1, long_m);
% 
for i = 1:long_m
    %  i 
    absData_a = abs(data_a(:, i + 1));
    % 
    [~, idx] = max(absData_a);
    % 
    maxTime(i) = 1000*time(idx); %smsv
end
maxTime = maxTime(:);
% 
fdata_a = [measurepoint, maxTime];

%% Step 2:  PSO  g(1), g(2), g(3)
N = 150; % 
D = 3;   % 
T = 150; % 
c1 = 0.8; % 
c2 = 0.8; % 
wmax = 0.9;
wmin = 0.2;
xmax = 100;
xmin = 0;
vmax = 0.5;
vmin = -0.5;

% 10gbestg
num_trials = 10;
best_gbest = inf;
best_g = zeros(1, D);

for trial = 1:num_trials
    % 
    x = rand(N, D) * (xmax - xmin) + xmin;
    v = rand(N, D) * (vmax - vmin) + vmin;
    p = x;
    pbest = inf(N, 1);
    
    % 
    for i = 1:N
        pbest(i) = fitness_g(x(i, :), fdata_a);
    end
    
    % 
    gbest = inf;
    g = ones(1, D);
    for i = 1:N
        if (pbest(i) < gbest)
            g = p(i, :);
            gbest = pbest(i);
        end
    end

    %  PSO 
    for i = 1:T
        for j = 1:N
            if (fitness_g(x(j, :), fdata_a) < pbest(j))
                p(j, :) = x(j, :);
                pbest(j) = fitness_g(x(j, :), fdata_a);
            end
            if (pbest(j) < gbest)
                g = p(j, :);
                gbest = pbest(j);
            end
            % 
            w = wmax - (wmax - wmin) * i / T;
            v(j, :) = w * v(j, :) + c1 * rand * (p(j, :) - x(j, :)) + c2 * rand * (g - x(j, :));
            x(j, :) = x(j, :) + v(j, :);

            % 
            x(j, :) = max(min(x(j, :), xmax), xmin);
            v(j, :) = max(min(v(j, :), vmax), vmin);
        end
    end
    
    % g
    if gbest < best_gbest
       best_gbest = gbest;
       best_g = g;
    end
end

distances = zeros(long_m, 1);
%  g 
for i = 1:size(measurepoint, 1)
    distances(i) = sqrt((measurepoint(i, 1) - best_g(1))^2 + (measurepoint(i, 2) - best_g(2))^2);
end
% 
% data_v = readmatrix('C:\Users\lhcivil\Desktop\PSO_weizhi_and_dangliang\PSO_weizhi_and_dangliang\vals.txt');


acceleration = data_a(:, 2:long_m + 1); % 
% 
velocity = cumtrapz(time, acceleration);
% 
data_v = [time, velocity];

% 
max_v = zeros(1, long_m);
% 
for i = 1 : long_m
    %  i 
    absData_v = abs(data_v(:, i + 1));
    % 
    max_v(i) = 100*max(absData_v); %m/scm/s
end
max_v = max_v(:);
% 
fdata_v = [measurepoint, max_v];

% 
%  V  R 
V = fdata_v(:, 1);
R = fdata_v(:, 2);
% 
lnV = log(V); %  ln(V)
lnR = log(R); %  ln(R)

% 
% y = lnV, x = lnR, lnV = C - *lnR
%  Z = [1, -lnR]' lnV = Z * [C; ]

% 
Z = [ones(length(lnR), 1), -lnR]; %  -ln(R)

% 
coeff = (Z' * Z) \ (Z' * lnV); % 
C = coeff(1); % 
alpha = coeff(2); %  

% PSO
D = 2;
xlimit = [50 300; 0 20];

% 
x = [rand(N, 1) * (xlimit(1, 2) - xlimit(1, 1)) + xlimit(1, 1), ...
     rand(N, 1) * (xlimit(2, 2) - xlimit(2, 1)) + xlimit(2, 1)];
v = rand(N, D) * (vmax - vmin) + vmin;
p = x;
pbest = inf(N, 1);

% 
for i = 1:N
    pbest(i) = fitness_k(x(i, :), fdata_v, alpha);
end

% 
gbest = inf;
g = ones(1, D);
for i = 1:N
    if (pbest(i) < gbest)
        g = p(i, :);
        gbest = pbest(i);
    end
end

gb = zeros(T, 1); % Tgbest

%  PSO 
best_gbest_2 = inf;
best_g_2 = zeros(1, D);

% 50PSO
num_trials_2 = 50;
for trial_2 = 1:num_trials_2
    for i = 1:T
        for j = 1:N
            if (fitness_k(x(j, :), fdata_v, alpha) < pbest(j))
                p(j, :) = x(j, :);
                pbest(j) = fitness_k(x(j, :), fdata_v, alpha);
            end
            if (pbest(j) < gbest)
                g = p(j, :);
                gbest = pbest(j);
            end
            % 
            w = wmax - (wmax - wmin) * i / T;
            v(j, :) = w * v(j, :) + c1 * rand * (p(j, :) - x(j, :)) + c2 * rand * (g - x(j, :));
            x(j, :) = x(j, :) + v(j, :);

            % 
            for ii = 1:D
                if (v(j, ii) > vmax) || (v(j, ii) < vmin)
                    v(j, ii) = rand * (vmax - vmin) + vmin;
                end
                if (x(j, ii) > xlimit(ii, 2)) || (x(j, ii) < xlimit(ii, 1))
                    x(j, ii) = rand * (xlimit(ii, 2) - xlimit(ii, 1)) + xlimit(ii, 1);
                end
            end
        end
        gb(i) = gbest; % gbest
    end

    % g
    if gbest < best_gbest_2
        best_gbest_2 = gbest;
        best_g_2 = g;
    end
end


end

%% 
function y = fitness_g(x, fdata)
    % 
    y = 0;
    long = length(fdata);
    for i = 1:(long - 1)
        for j = (i + 1):long
            y = y + abs(sqrt((x(1) - fdata(i, 1))^2 + (x(2) - fdata(i, 2))^2) ...
                       - sqrt((x(1) - fdata(j, 1))^2 + (x(2) - fdata(j, 2))^2) ...
                       - x(3) * (fdata(i, 3) - fdata(j, 3)));
        end
    end
end

function y = fitness_k(x, result, alpha)
    % 
    y = 0;
    long = length(result);
    for i = 1:long
        y = y + abs(log(x(1)) + alpha / 3 * log(x(2)) - log(result(i, 1)) - alpha * log(result(i, 2)));
    end
end
 