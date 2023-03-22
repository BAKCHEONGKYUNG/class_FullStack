import React, { useCallback, useState, useEffect } from 'react';
import { StatusBar, Dimensions, Alert, TouchableOpacity } from 'react-native';
import styled, { ThemeProvider } from 'styled-components/native';
import { theme } from './theme';
import Input from './components/Input';
import Task from './components/Task';
import AsyncStorage from '@react-native-async-storage/async-storage';
import * as SplashScreen from 'expo-splash-screen';

const Container = styled.SafeAreaView`
  flex: 1;
  background-color: ${({ theme }) => theme.background};
  align-items: center;
  justify-content: flex-start;
`;

const Title = styled.Text`
  font-size: 40px;
  font-weight: 600;
  color: ${({ theme }) => theme.main};
  align-self: flex-start;
  padding-left: 10px;
  margin: 20px 0px 20px 20px; /* y축 x축 */
`;

const List = styled.ScrollView`
  flex: 1;
  width: ${({ width }) => width - 40}px;
`;

const DellBtn = styled.View`
  justify-content: flex-end;
  bottom: 20px;
  left: 0px;
  right: 0px;
  background-color: ${({ theme }) => theme.main};
  padding: 15px 20px 15px 20px;
  margin: 10px;
  border-radius: 5px;
`;
const DellBtnText = styled.Text`
  color: #313131;
  padding: 0px 90px 0px 90px;
  text-align: center;
  font-weight: 600;
  font-size: 20px;
`;

// Keep the splash screen visible while we fetch resources
SplashScreen.preventAutoHideAsync();

const App = () => {
  const width = Dimensions.get('window').width;

  const [isReady, setIsReady] = useState(false);

  const [newTask, setNewTask] = useState('');

  const [tasks, setTasks] = useState([]);

  const _saveTasks = async tasks => {
    try {
      await AsyncStorage.setItem('tasks', JSON.stringify(tasks));
      setTasks(tasks);
    } catch (e) {
      console.error(e);
    }
  };

  const _loadTasks = async () => {
    const loadedTasks = await AsyncStorage.getItem('tasks');
    setTasks(JSON.parse(loadedTasks || '{}'));
  };

  useEffect(() => {
    async function prepare() {
      try {
        await _loadTasks();
      } catch (e) {
        console.error(e);
      } finally {
        setIsReady(true);
      }
    }

    prepare();
  }, []);

  const onLayoutRootView = useCallback(async () => {
    if (isReady) {
      await SplashScreen.hideAsync();
    }
  }, [isReady]);

  if (!isReady) {
    return null;
  }

  const _addTask = () => {
    const ID = Date.now().toString();
    const newTaskObject = {
      [ID]: { id: ID, text: newTask, completed: false },
    };

    setNewTask('');
    _saveTasks({ ...tasks, ...newTaskObject });
  };

  const _deleteTask = id => {
    const currentTasks = { ...tasks }; //Object.assign({},tasks);

    Alert.alert('', '삭제하시겠습니까?', [
      {
        text: '아니오',
        onPress() {
          console.log('아니오');
        },
      },
      {
        text: '예',
        onPress() {
          console.log('예');
          delete currentTasks[id];
          _saveTasks(currentTasks);
        },
      },
    ]);
  };

  const _toggleTask = id => {
    const currentTasks = { ...tasks };
    currentTasks[id]['completed'] = !currentTasks[id]['completed'];
    _saveTasks(currentTasks);
  };

  const _updateTask = item => {
    const currentTasks = { ...tasks };
    currentTasks[item.id] = item;
    _saveTasks(currentTasks);
  };

  const _handleTextChange = text => {
    setNewTask(text);
    console.log(`변경된문자열:${newTask}`);
  };

  const _onBlur = () => {
    setNewTask('');
  };

  const _handleDeleteAllTask = async () => {
    const currentTasks = { ...tasks };
    for (const id in currentTasks) {
      if (currentTasks[id]['completed']) {
        delete currentTasks[id];
      }
    }
    setTasks(currentTasks);
    await AsyncStorage.setItem('tasks', JSON.stringify(currentTasks));
  };

  return isReady ? (
    <ThemeProvider theme={theme}>
      <Container onLayout={onLayoutRootView}>
        <StatusBar
          barStyle="light-content"
          backgroundColor={theme.background}
        />
        <Title>BUCKET List</Title>
        <Input
          value={newTask}
          placeholder="+ 항목추가"
          onChangeText={_handleTextChange} //수정시
          onSubmitEditing={_addTask} //완료버튼
          onBlur={_onBlur} //포커스 잃었을때
        />
        <List width={width}>
          {Object.values(tasks)
            .reverse()
            .map(item => (
              <Task
                key={item.id}
                item={item}
                deleteTask={_deleteTask}
                toggleTask={_toggleTask}
                updateTask={_updateTask}
              />
            ))}
        </List>
        <TouchableOpacity onPress={_handleDeleteAllTask}>
          <DellBtn>
            <DellBtnText>완료된 항목 전체 삭제</DellBtnText>
          </DellBtn>
        </TouchableOpacity>
      </Container>
    </ThemeProvider>
  ) : (
    <AppLoadingProps
      startAsync={_loadTasks}
      onFinish={() => setIsReady(true)}
      onError={console.error}
    />
  );
};

export default App;
