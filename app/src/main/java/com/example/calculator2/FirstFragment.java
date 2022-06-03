package com.example.calculator2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.calculator2.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private List<String> values = new LinkedList<>();

    private int currentValue = 0;

    private boolean isDotWasPushed;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.sign.setOnClickListener(v -> {
            currentValue = -currentValue;
            displayCurrentValue();
        });

        binding.get.setOnClickListener(v -> {
            values.add(String.valueOf(currentValue));
            displayOperationsList();
            currentValue = 0;
            displayCurrentValue();
        });

        binding.dot.setOnClickListener(
                v -> {
                    isDotWasPushed = true;
                    displayCurrentValue();
                }
        );

        binding.percent.setOnClickListener(
                ignored -> binding.yourValues.setText("%"));

        binding.devide.setOnClickListener(
                ignored -> {
                    values.add(String.valueOf(currentValue));
                    values.add("/");
                    displayOperationsList();
                    currentValue = 0;
                    displayCurrentValue();
                });

        binding.seven.setOnClickListener(
                ignored -> appendDigit(7));

        binding.eight.setOnClickListener(
                ignored -> appendDigit(8));

        binding.nine.setOnClickListener(
                ignored -> appendDigit(9));

        binding.multiply.setOnClickListener(
                ignored -> {
                    values.add(String.valueOf(currentValue));
                    values.add("*");
                    displayOperationsList();
                    currentValue = 0;
                    displayCurrentValue();
                });

        binding.four.setOnClickListener(
                ignored -> appendDigit(4));

        binding.five.setOnClickListener(
                ignored -> appendDigit(5));

        binding.six.setOnClickListener(
                ignored -> appendDigit(6));

        binding.subtract.setOnClickListener(
                ignored -> {
                    values.add(String.valueOf(currentValue));
                    values.add("-");
                    displayOperationsList();
                    currentValue = 0;
                    displayCurrentValue();
                });

        binding.one.setOnClickListener(
                ignored -> appendDigit(1));

        binding.two.setOnClickListener(
                ignored -> appendDigit(2));

        binding.three.setOnClickListener(
                ignored -> appendDigit(3));

        binding.add.setOnClickListener(

                ignored -> {
                    values.add(String.valueOf(currentValue));
                    values.add("+");
                    displayOperationsList();
                    currentValue = 0;
                    displayCurrentValue();
                });

        binding.zero.setOnClickListener(
                ignored -> appendDigit(0));

        binding.reset.setOnClickListener(
                ignored -> {
                    values.clear();
                    displayOperationsList();
                    currentValue = 0;
                    displayCurrentValue();
                });

        binding.result.setOnClickListener(

                ignored -> {
                    double result = evaluate(values);
                    binding.yourValues.setText(String.valueOf(result));
                    values.clear();
                    displayOperationsList();

                });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void appendDigit(int value) {


        if (currentValue < 0) {
            currentValue = currentValue * 10 - value;
        } else {
            currentValue = currentValue * 10 + value;
        }
        displayCurrentValue();
    }

    private void displayCurrentValue() {
        binding.yourValues.setText(String.valueOf(currentValue));
    }


    private void displayOperationsList() {
        binding.operations.setText(String.join(" ", values));
    }

    public double evaluate(List<String> values) {

        if (values.isEmpty()) {
            return 0;
        }

//      -проверить каждый элемент массива (явлеятся ли он числом или арифметической операцией)
//        если является числом то кладем его в стек
//        если является арифметической операцией то надо с вершины стека достать два значения (с удалением),
//        провести с ними эту операцию и результат положить в стек
//        окончательным результатом решения задачи будет элемент вершины стека (в идеале, он там единственный)

        Deque<Double> accumulator = new LinkedList<>();

        for (int i = 0; i < values.size(); i++) {

            switch (values.get(i)) {
                case "+": {
                    double firstOperand = accumulator.pop();
                    double secondOperand = accumulator.pop();
                    double sum = firstOperand + secondOperand;
                    accumulator.push(sum);
                    break;
                }
                case "-": {
                    double firstOperand = accumulator.pop();
                    double secondOperand = accumulator.pop();
                    double result = secondOperand - firstOperand;
                    accumulator.push(result);
                    break;
                }
                case "*": {
                    double firstOperand = accumulator.pop();
                    double secondOperand = accumulator.pop();
                    double result = secondOperand * firstOperand;
                    accumulator.push(result);
                    break;
                }
                case "/": {
                    double firstOperand = accumulator.pop();
                    double secondOperand = accumulator.pop();
                    double result = secondOperand / firstOperand;
                    accumulator.push(result);
                    break;
                }

                default: {
                    double result = Double.parseDouble(values.get(i));
                    accumulator.push(result);
                    break;
                }
            }
        }
        return accumulator.pop();
    }
}