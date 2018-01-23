function [theta] = trainCatFace()

x = toascii(strrep(fileread("CatFace_perfect_cat_image.txt"),"\n",""));

x = [x; ones(1,195)*43; ones(1,195)*32];
y = [1; 0; 0];

theta_initial = zeros(size(x, 2), 1);

costFunction = @(t) costFunction(t, x, y);

% Now, costFunction is a function that takes in only one argument
options = optimset('MaxIter', 200, 'GradObj', 'on');

% Minimize using fmincg
theta = fminunc(costFunction, theta_initial, options);

csvwrite('catface_logistic_theta.csv', theta');
